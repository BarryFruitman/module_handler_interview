package com.scribd.presentationia.modules.document_carousel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scribd.domain.entities.ModuleType
import com.scribd.domain.entities.DocumentCarouselModuleEntity
import com.scribd.domain.entities.ModuleEntity
import com.scribd.presentationia.thumbnail.ThumbnailModel
import com.scribd.presentationia.modules.ModuleViewModel
import com.scribd.presentationia.thumbnail.ThumbnailDataTransformer
import javax.inject.Inject

class DocumentCarouselModuleViewModel(
    override val context: ModuleViewModel.ModuleContext
): ViewModel(), ModuleViewModel {

    override val moduleType = ModuleType.DOCUMENT_CAROUSEL

    private val _thumbnailModels = MutableLiveData<List<ThumbnailModel>>()
    val thumbnailModels: LiveData<List<ThumbnailModel>> = _thumbnailModels

    @Inject
    lateinit var thumbnailDataTransformer: ThumbnailDataTransformer

    init {
//        AppComponentProvider.getAppComponent().inject(this)
    }

    override fun bindModule(module: ModuleEntity) {
        module as? DocumentCarouselModuleEntity ?: throw IllegalArgumentException("Wrong module type")
        _thumbnailModels.value = module.documents.mapNotNull { document -> thumbnailDataTransformer.transformToThumbnailModel(document, 0F, true, false) }
    }
}
