package com.scribd.presentationia.modules

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribd.domain.entities.DiscoverModuleResponse
import com.scribd.domain.entities.ModuleEntity
import com.scribd.presentationia.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.UUID

class ModuleListViewModel(val arguments: Bundle) : ViewModel() {

    companion object {
        const val TAG = "ModuleListViewModel"

        const val ARG_INTEREST_ID = "interest_id"
        const val ARG_CONTENT_TYPE = "content_type"
        const val ARG_HEADER_TEXT = "header_text"
        const val ARG_PAGE_TITLE = "page_title"
    }

    interface ModuleList {
        suspend fun fetchModules(): DiscoverModuleResponse?
        val context: ModuleViewModel.ModuleContext
    }

    val context: ModuleViewModel.ModuleContext
        get() = moduleList.context

    val baseContext = object : ModuleViewModel.ModuleContext {
        override var compilationId: String? = null

        override var pageViewUUID = UUID.randomUUID()
        override val interestId = arguments.getInt(ARG_INTEREST_ID)
        override val contentType = arguments.getString(ARG_CONTENT_TYPE) ?: "mixed"
        override val pageTitle = arguments.getString(ARG_PAGE_TITLE) ?: ""
        override val headerText = arguments.getString(ARG_HEADER_TEXT) ?: ""
        override val viewModelScope = this@ModuleListViewModel.viewModelScope

        override fun onRecommendationView(recId: String) = this@ModuleListViewModel.analyticsManager.logRecommendationView(recId)
        override fun onRecommendationClick(recId: String) = this@ModuleListViewModel.analyticsManager.logRecommendationClick(recId)
    }

    val contentState: MutableLiveData<State> = MutableLiveData()

    private lateinit var modules: List<ModuleEntity>
    private val moduleList: ModuleList = object : ModuleList {
        override suspend fun fetchModules(): DiscoverModuleResponse? {
            TODO("Not yet implemented")
        }
        override val context: ModuleViewModel.ModuleContext
            get() = TODO("Not yet implemented")
    }
    private val analyticsManager: ModuleListAnalyticsManager
    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + completableJob)

    val moduleCount: Int
        get() = modules.size

    init {
        @Suppress("LeakingThis")
//        moduleList = ModuleListFactory(baseContext).provideModuleListFor("promo_drawer")
        analyticsManager = ModuleListAnalyticsManager(context)
    }

    fun fetchModules() {
        coroutineScope.launch {
            moduleList.fetchModules()?.let { response ->
                modules = response.modules
                context.compilationId = response.compilationId

                contentState.value = State.OK_HIDDEN

                onPageView() // Log page view now that we have a compilation id
            } ?: run {
                contentState.value = State.GENERIC_ERROR
            }
        }
    }

    fun getModuleAt(position: Int) = modules[position]

    fun getModuleType(position: Int) = modules[position].type.id

    private fun onPageView() = analyticsManager.logPageView()

    fun onModuleVisible(position: Int) {
        modules.getOrNull(position)?.let { module ->
            analyticsManager.logModuleView(module)
        }
    }

    private var visibleToUser: Boolean = false

    fun onVisible() {
        visibleToUser = true
    }

    fun onHidden(isChangingConfigurations: Boolean, isFinishing: Boolean) {
        visibleToUser = false
    }
}