package com.scribd.presentationia.modules

import com.scribd.domain.entities.DiscoverModuleResponse
import com.scribd.domain.entities.InlineAction
import com.scribd.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PromoDrawerModuleList(baseContext: ModuleViewModel.ModuleContext): ModuleListViewModel.ModuleList {
    @Inject
    lateinit var caseToViewModules: CaseToViewPromoDrawer

    @Inject
    lateinit var caseToNavigateToBookPage: CaseToNavigateToBookPage

    @Inject
    lateinit var caseToHidePromoDrawer: CaseToNavigateHidePromoDrawer

    var promoId: Int? = null

    init {
//        AppComponentProvider.getAppComponent().inject(this)
    }

    override suspend fun fetchModules(): DiscoverModuleResponse? {
        val promoResponse = (caseToViewModules.launch(Unit) as? CaseToViewModulesNew.Response.Success)?.response as? PromoDiscoverModuleResponse
        promoId = promoResponse?.promoId

        return promoResponse
    }

    override val context = object : ModuleViewModel.ModuleContext by baseContext {
        override suspend fun onNavigate() {
            super.onNavigate()

            // Close drawer before returning
            suspendCoroutine<Boolean> { cont ->
                CoroutineScope(Dispatchers.Default).launch {
                    promoId?.let {
                        caseToHidePromoDrawer.launch(
                            CaseToNavigateHidePromoDrawer.In(
                                it,
                                "X_CLICKED",
                                InlineAction { cont.resume(true) }
                            )
                        )
                    }
                }
            }
        }
    }
}