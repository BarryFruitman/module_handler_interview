package com.scribd.presentation.modules.binders.hero_content

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ModuleHeroContentNewBinding
import com.scribd.presentation.modules.ModuleBinder
import com.scribd.presentation.modules.ModuleListFragment
import com.scribd.presentation.modules.ModuleViewHolder
import com.scribd.presentationia.modules.hero_content.HeroContentModuleViewModel

class HeroContentModuleBinder(val fragment: ModuleListFragment):
    ModuleBinder<HeroContentModuleBinder.HeroContentViewHolder, HeroContentModuleViewModel> {

    class HeroContentViewHolder(root: View, moduleBinding: HeroContentModuleViewModel) : ModuleViewHolder(root, moduleBinding)

    override val layoutId = R.layout.module_hero_content_new

    override val moduleViewModel = HeroContentModuleViewModel(fragment.viewModel.context)

    override fun createViewHolder(root: View, moduleModel: HeroContentModuleViewModel): HeroContentViewHolder {
        val binding = DataBindingUtil.getBinding<ModuleHeroContentNewBinding>(root) ?: throw IllegalStateException("can't find data binding")
        binding.model = moduleModel

        return HeroContentViewHolder(root, moduleModel)
    }
}