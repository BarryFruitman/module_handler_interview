package com.scribd.presentationia.modules

import com.scribd.domain.entities.ModuleEntity
import com.scribd.domain.entities.ModuleType
import kotlinx.coroutines.CoroutineScope
import java.util.*

interface ModuleViewModel {
    val moduleType: ModuleType
    val context: ModuleContext

    fun bindModule(module: ModuleEntity)

    fun onModuleVisible() = Unit

    interface ModuleContext {
        var compilationId: String?
        var pageViewUUID: UUID

        val contentType: String
        val interestId: Int
        val pageTitle: String
        val headerText: String
        val viewModelScope: CoroutineScope

        /**
         * Called when the module row wants to be closed
         */
        fun closeModule(module: ModuleEntity) = Unit

        /**
         * Called when a module is initiating a navigation event. Override this to
         * perform clean transitions.
         */
        suspend fun onNavigate() = Unit

        /**
         * Called when the module was clicked (but doesn't handle the event)
         */
        fun onModuleClick(module: ModuleEntity) = Unit

        /**
         * Called when the module was long-clicked (but doesn't handle the event)
         */
        fun onModuleLongClick(module: ModuleEntity) = Unit

        /**
         *  Called when a recommendation becomes visible. Log analytics.
         */
        fun onRecommendationView(recId: String)

        /**
         * Called when a recommendation is clicked. Log analytics.
         */
        fun onRecommendationClick(recId: String)

        // Callback for modules to notify their context

        // TODO: Map these ModuleDelegate methods to ModuleContext or find a way to obsolete them
//    fun notifyRecyclerViewRowOpened(position: Int) = Unit // Replaced by onModuleClick()

        // These belong in ModuleList or extensions of ModuleContext
//    fun notifySetTitleOnScroll(scrollOffset: Int, title: String, subtitle: String) = Unit
//    fun notifyFiltersChange(newFilters: DocumentFiltersWrapper) = Unit
//    fun notifyOverflowOptionClicked(position: Int, option: ListItemOverflowController.Option) = Unit
//    fun notifyItemAction(itemAction: ModuleItemAction<*>)

    }
}
