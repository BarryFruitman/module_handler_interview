package com.scribd.presentationia.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribd.domain.entities.InlineAction
import com.scribd.domain.usecase.CaseToNavigateHidePromoDrawer
import kotlinx.coroutines.launch
import javax.inject.Inject

class PromoDrawerViewModel : ViewModel() {

    @Inject
    lateinit var caseToHidePromoDrawer: CaseToNavigateHidePromoDrawer

    var promoId: Int = 0

    init {
//        AppComponentProvider.getAppComponent().inject(this)
    }

    fun postDrawerClosed(closeType: String, animationEndAction: () -> Unit = {}) = viewModelScope.launch {
        caseToHidePromoDrawer.launch(
            CaseToNavigateHidePromoDrawer.In(
                promoId,
                closeType,
                InlineAction(animationEndAction)
            )
        )
    }
}