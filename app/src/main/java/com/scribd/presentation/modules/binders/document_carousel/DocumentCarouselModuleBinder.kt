package com.scribd.presentation.modules.binders.document_carousel

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ModuleCarouselHorizontalNewBinding
import com.scribd.presentation.modules.ModuleBinder
import com.scribd.presentation.modules.ModuleListFragment
import com.scribd.presentation.modules.ModuleViewHolder
import com.scribd.presentationia.modules.document_carousel.DocumentCarouselModuleViewModel

class DocumentCarouselModuleBinder(val fragment: ModuleListFragment) :
    ModuleBinder<DocumentCarouselModuleBinder.DocumentCarouselViewHolder, DocumentCarouselModuleViewModel> {

    class DocumentCarouselViewHolder(root: View, moduleBinding: DocumentCarouselModuleViewModel) : ModuleViewHolder(root, moduleBinding) {
        var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
    }

    override val layoutId = R.layout.module_carousel_horizontal_new

    override val moduleViewModel = DocumentCarouselModuleViewModel(fragment.viewModel.context)

    override fun createViewHolder(root: View, moduleModel: DocumentCarouselModuleViewModel): DocumentCarouselViewHolder {
        val binding = DataBindingUtil.getBinding<ModuleCarouselHorizontalNewBinding>(root) ?: throw IllegalStateException("can't find data binding")
        binding.model = moduleModel

        val adapter = CarouselAdapter()
        binding.recyclerView.adapter = adapter
        moduleModel.thumbnailModels.observe(fragment.viewLifecycleOwner) { thumbnailModels ->
            (binding.recyclerView.adapter as CarouselAdapter).thumbnailModels = thumbnailModels
        }

        return DocumentCarouselViewHolder(root, moduleModel)
    }
}