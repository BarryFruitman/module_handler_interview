@file:Suppress("DEPRECATION")

package com.scribd.app.discover_modules.document_list_item

import android.content.res.Resources
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemDocumentBinding
import com.scribd.app.DocUtils
import com.scribd.app.ScribdApp
import com.scribd.app.discover_modules.*
import com.scribd.dataia.Analytics
import com.scribd.presentation.AdapterWithAnalytics
import com.scribd.presentationia.PodcastEpisodeMetadataContract
import com.scribd.presentation.modules.SummaryMetadataPresenter
import com.scribd.dataia.Document
import com.scribd.presentationia.thumbnail.ThumbnailViewModel
//import javax.inject.Inject

class DocumentListItemModuleHandler(fragment: Fragment, moduleDelegate: ModuleDelegate)
    : ModuleHandler<BasicDiscoverModuleWithMetadata, DocumentListItemViewHolder>(fragment, moduleDelegate) {

//    @Inject
    lateinit var podcastEpisodeMetadataPresenter: PodcastEpisodeMetadataContract.Presenter

    private val resources: Resources = fragment.resources
    private val thumbnailHeight = resources.getDimensionPixelSize(R.dimen.document_card_thumbnail_height)
    private val thumbnailWidth = resources.getDimensionPixelSize(R.dimen.document_card_thumbnail_width)
    private val thumbnailViewModel: ThumbnailViewModel by fragment.viewModels()
    private lateinit var binding: ListItemDocumentBinding

    init {
//        AppComponentProvider.getAppComponent().inject(this)
    }

    override fun handleView(basicModule: BasicDiscoverModuleWithMetadata,
                            holder: DocumentListItemViewHolder,
                            position: Int,
                            parentAdapter: AdapterWithAnalytics<*>
    ) {

        val discoverModule = basicModule.dataObject
        val document = discoverModule.documents[0]
        val referrer = "referrer"

        val summaryMetadataPresenter = SummaryMetadataPresenter(holder.summaryOfPrefix, holder.caption)
        podcastEpisodeMetadataPresenter.view = holder

        when {
            document.isPodcastSeries -> setThumbnail(holder, document, thumbnailWidth, thumbnailWidth)
            document.isIssue -> setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
            document.isCanonical -> setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
            document.isBook -> setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
            document.isAudioBook -> setThumbnail(holder, document, thumbnailWidth, thumbnailWidth)
            document.isPodcastEpisode -> setThumbnail(holder, document, thumbnailWidth, thumbnailWidth)
            document.isUgc -> setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
            document.isSheetMusic -> setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
            document.isArticle -> {
                if (document.hasRegularImage() || document.hasSquareImage()) {
                    setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
                    holder.thumbnail.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }
            document.isCanonicalSummary -> setThumbnail(holder, document, thumbnailHeight, thumbnailWidth)
        }

        holder.thumbnail.setOnLongClickListener {
            val message: String? = if (document.isCanonicalSummary) {
                fragment.getString(
                    R.string.key_insights_from_author_snapshot,
                    document.firstAuthorOrPublisherName,
                    document.title)
            } else {
                document.title
            }
            Toast.makeText(fragment.context, message, Toast.LENGTH_LONG).show()
            true
        }

        holder.thumbnail.setOnClickListener {
            launchBookPage(document, referrer)
        }

        holder.title.text = document.title

        // Author
        when {
            document.isUgc -> {
                holder.author.visibility = View.GONE
                holder.uploadedBy.visibility = View.VISIBLE
                holder.uploadedBy.setUsername(DocUtils.getAuthorsText(document))
            }
            document.isPodcastEpisode -> podcastEpisodeMetadataPresenter.setupMetadata(document)
            document.isPodcastSeries -> holder.author.visibility = View.GONE
            document.isCanonicalSummary -> {
                holder.uploadedBy.visibility = View.GONE
                holder.author.visibility = View.GONE
            }
            else -> {
                holder.uploadedBy.visibility = View.GONE
                holder.author.visibility = View.VISIBLE
                holder.author.text = DocUtils.getAuthorsText(document)
            }
        }

        holder.saveForLater.setDocument(document, Analytics.LibraryPanel.Source.list_item)

        if (document.isCanonicalSummary) {
            summaryMetadataPresenter.show(document)
        } else {
            summaryMetadataPresenter.hide()
            setupLengthAndReadingTimeCaption(document, holder, false)
        }

        holder.progressBar.visibility = View.GONE

        holder.selectionOverlayLayout.visibility = View.GONE

        holder.itemView.setOnClickListener {
            launchBookPage(document, referrer)
        }

        if (document.isAudioBook) {
            holder.finishedStateView.setFinishedText(ScribdApp.getInstance().getString(R.string.mylibrary_finished_audiobook))
        }
    }

    private fun setupLengthAndReadingTimeCaption(document: Document, holder: DocumentListItemViewHolder, shouldShowTimeRemaining: Boolean) {
        if (document.isPodcastEpisode && !shouldShowTimeRemaining) {
            podcastEpisodeMetadataPresenter.updateRuntime(document)
            return
        }

        var totalLengthOrReadingTimeText = DocUtils.getLengthString(document)

        val readingLengthOrTimeRemainingText = if (shouldShowTimeRemaining) {
            DocUtils.getRemainingLengthOrTimeDisplayString(document)
        } else {
            DocUtils.estimatedTime(ScribdApp.getInstance().resources, document)
        }

        if (!TextUtils.isEmpty(readingLengthOrTimeRemainingText)) {
            val resources = fragment.resources
            totalLengthOrReadingTimeText = resources.getString(R.string.length_and_read_time_caption, totalLengthOrReadingTimeText,
                readingLengthOrTimeRemainingText)
        }
        holder.caption.visibility = View.VISIBLE
        holder.caption.text = totalLengthOrReadingTimeText
    }

    override fun getLayoutId(): Int = R.layout.list_item_document

    override fun createViewHolder(itemView: View): DocumentListItemViewHolder = DocumentListItemViewHolder(binding)

    override fun canHandle(discoverModule: DiscoverModule): Boolean = DiscoverModule.Type.client_document_list_item.name == discoverModule.type

    override fun isDataValid(discoverModule: DiscoverModule): Boolean = discoverModule.documents?.isNotEmpty() == true

    override fun createDiscoverModuleWithMetadata(
        discoverModule: DiscoverModule,
        metadata: DiscoverModuleWithMetadata.ModuleMetadata): BasicDiscoverModuleWithMetadata =
        BasicDiscoverModuleWithMetadataFactory(this, discoverModule, metadata).createSingleDocumentModule()

    override fun areItemsTheSame(oldDiscoverModuleWithMetadata: BasicDiscoverModuleWithMetadata,
                                 newDiscoverModuleWithMetadata: BasicDiscoverModuleWithMetadata): Boolean {

        val oldServerId = oldDiscoverModuleWithMetadata.discoverModule.documents.first().serverId
        val newServerId = newDiscoverModuleWithMetadata.discoverModule.documents.first().serverId

        return oldServerId == newServerId
    }

    override fun areContentsTheSame(oldDiscoverModuleWithMetadata: BasicDiscoverModuleWithMetadata,
                                    newDiscoverModuleWithMetadata: BasicDiscoverModuleWithMetadata): Boolean {
        val oldDiscoverModule = oldDiscoverModuleWithMetadata.discoverModule
        val newDiscoverModule = newDiscoverModuleWithMetadata.discoverModule

        return oldDiscoverModule.auxData == newDiscoverModule.auxData
    }

    private fun loadThumbnailAsync(document: Document, holder: DocumentListItemViewHolder) {
        thumbnailViewModel.setupThumbnail(document.serverId, fragment.viewLifecycleOwner, { model ->
            holder.thumbnail.model = model
        }, holder)
    }

    override fun onViewRecycled(holder: DocumentListItemViewHolder) {
        super.onViewRecycled(holder)
        thumbnailViewModel.resetThumbnail(holder)
        holder.thumbnail.model = null
    }

    override fun inflateView(parent: ViewGroup): View {
        binding = ListItemDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return binding.root
    }

    private fun setThumbnail(holder: DocumentListItemViewHolder, document: Document, height: Int, width: Int) {
        holder.thumbnail.apply {
            thumbnailHeight = height
            thumbnailWidth = width
            visibility = View.VISIBLE
        }
        loadThumbnailAsync(document, holder)
    }

    private fun launchBookPage(document: Document, referrer: String) {
        fragment.activity?.let { activity ->
            // Launch book page
//            val builder = DocumentLauncher.Builder.create(activity)
//                .setReferrer(referrer)
//                .setDocument(document)
//            builder.launch()
        }
    }
}