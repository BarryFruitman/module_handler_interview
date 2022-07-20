package com.scribd.domain.usecase

import com.scribd.domain.entities.InlineAction

interface CaseToNavigateHidePromoDrawer {
    class In(it: Int, s: String, inlineAction: InlineAction) {

    }

    fun launch(any: Any)

}

