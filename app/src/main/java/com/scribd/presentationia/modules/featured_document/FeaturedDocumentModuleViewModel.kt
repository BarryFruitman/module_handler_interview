package com.scribd.presentationia.modules.featured_document

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scribd.domain.entities.ModuleType
import com.scribd.domain.entities.FeaturedDocumentModuleEntity
import com.scribd.domain.entities.ModuleEntity
import com.scribd.domain.usecase.CaseToNavigateToBookPage
import com.scribd.presentationia.thumbnail.ThumbnailModel
import com.scribd.presentationia.modules.ModuleViewModel
import com.scribd.presentationia.thumbnail.ThumbnailDataTransformer
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeaturedDocumentModuleViewModel(
    override val context: ModuleViewModel.ModuleContext
): ViewModel(), ModuleViewModel {

    @Inject
    lateinit var caseToNavigateToBookPage: CaseToNavigateToBookPage

    lateinit var module: FeaturedDocumentModuleEntity

    override val moduleType = ModuleType.FEATURED_DOCUMENT

    private val _title = MutableLiveData("Default Title")
    val title: LiveData<String> = _title
    private val _blurbQuote = MutableLiveData("Default Blurb Quote")
    val blurbQuote: LiveData<String> = _blurbQuote
    private val _blurbParagraph = MutableLiveData("Default Blurb Paragraph")
    val blurbParagraph: LiveData<String> = _blurbParagraph
    private val _thumbnail = MutableLiveData<ThumbnailModel>()
    val thumbnailModel: LiveData<ThumbnailModel?> = _thumbnail

    @Inject
    lateinit var thumbnailDataTransformer: ThumbnailDataTransformer

    init {
//        AppComponentProvider.getAppComponent().inject(this)
    }

    override fun bindModule(module: ModuleEntity) {
        this.module = module as? FeaturedDocumentModuleEntity ?: throw IllegalArgumentException("Wrong module type")

        _title.value = module.title
        _blurbQuote.value = module.blurbQuote
        _blurbParagraph.value = module.blurbParagraph
        _thumbnail.value = thumbnailDataTransformer.transformToThumbnailModel(module.document, 0F, true, false)
    }

    override fun onModuleVisible() {
        module.document.analyticsId?.let { context.onRecommendationView(it) }
    }

    fun onClickThumb() {
        context.viewModelScope.launch {
            // Log analytics
            module.document.analyticsId?.let { context.onRecommendationClick(it) }

            // Notify container of navigation
            context.onNavigate()

            // Navigate to book page
            caseToNavigateToBookPage.executeAsync(module.document.id, false, "referrer", null).await()
        }
    }
}
