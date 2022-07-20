package com.scribd.presentation.modules.binders.featured_document

import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ModuleFeaturedDocumentNewBinding
import com.scribd.presentation.modules.ModuleBinder
import com.scribd.presentation.modules.ModuleListFragment
import com.scribd.presentation.modules.ModuleViewHolder
import com.scribd.presentation.thumbnail.ThumbnailView
import com.scribd.presentationia.modules.featured_document.FeaturedDocumentModuleViewModel

class FeaturedDocumentModuleBinder(private val fragment: ModuleListFragment) :
    ModuleBinder<FeaturedDocumentModuleBinder.FeaturedDocumentViewHolder, FeaturedDocumentModuleViewModel> {

    class FeaturedDocumentViewHolder(root: View, moduleModel: FeaturedDocumentModuleViewModel) : ModuleViewHolder(root, moduleModel) {
        var thumbnail: ThumbnailView = itemView.findViewById(R.id.thumbnail)
    }

    override val layoutId = R.layout.module_featured_document_new

    override val moduleViewModel = FeaturedDocumentModuleViewModel(fragment.viewModel.context)

    override fun createViewHolder(root: View, moduleModel: FeaturedDocumentModuleViewModel): FeaturedDocumentViewHolder {
        val binding = DataBindingUtil.getBinding<ModuleFeaturedDocumentNewBinding>(root) ?: throw IllegalStateException("can't find data binding")
        binding.model = moduleModel

        moduleModel.thumbnailModel.observe(fragment.viewLifecycleOwner) { thumbnailModel ->
            binding.thumbnail.model = thumbnailModel
        }

        return FeaturedDocumentViewHolder(root, moduleModel)
    }

//    override fun isDataValid(discoverModule: DiscoverModule): Boolean {
//        if (discoverModule.documents == null || discoverModule.documents.size < 1) {
//            return false
//        }
//        val document = discoverModule.documents[0]
//        return document != null && document.editorialBlurb != null && !TextUtils.isEmpty(document.editorialBlurb.header) &&
//                !TextUtils.isEmpty(document.editorialBlurb.description) && !TextUtils.isEmpty(
//            document.editorialBlurb
//                .title
//        )
//    }
}