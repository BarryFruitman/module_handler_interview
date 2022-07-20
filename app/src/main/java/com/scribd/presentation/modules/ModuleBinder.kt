package com.scribd.presentation.modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.scribd.presentation.modules.binders.document_carousel.DocumentCarouselModuleBinder
import com.scribd.presentation.modules.binders.featured_document.FeaturedDocumentModuleBinder
import com.scribd.presentation.modules.binders.hero_content.HeroContentModuleBinder
import com.scribd.presentationia.modules.ModuleViewModel

interface ModuleBinder<VH : ModuleViewHolder, VM: ModuleViewModel> {

    fun createViewHolder(parent: ViewGroup): VH = createViewHolder(inflate(parent), moduleViewModel)

    val moduleType: String
        get() = moduleViewModel.moduleType.id

    /**
     * Layout id for module type
     */
    val layoutId: Int

    /*
     * View model for this binder
     */
    val moduleViewModel: VM

    /**
     * Override this and create the view holder and bind a module model to it
     */
    fun createViewHolder(root: View, moduleModel: VM): VH

    private fun inflate(parent: ViewGroup): View =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false).root
}

class RegisteredModuleBinders(val fragment: ModuleListFragment) {
    private val binders: Array<ModuleBinder<out ModuleViewHolder, out ModuleViewModel>> =
        arrayOf(
            HeroContentModuleBinder(fragment),
            FeaturedDocumentModuleBinder(fragment),
            DocumentCarouselModuleBinder(fragment)
        )

    private val viewTypeMap = binders.map { binder ->
        binder.moduleType.hashCode() to binder
    }.toMap()

    fun binderForViewType(viewType: Int) =
        viewTypeMap[viewType] ?: throw IllegalArgumentException("view type not found")

    private val moduleTypeMap = binders.map { binder ->
        binder.moduleType to binder.moduleViewModel
    }.toMap()

    fun binderForModuleType(moduleType: String): ModuleViewModel =
        moduleTypeMap[moduleType] ?: throw IllegalArgumentException("view type not found")
}
