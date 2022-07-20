@file:Suppress("DEPRECATION")

package com.scribd.app.discover_modules.document_list_item

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myapplication.databinding.ListItemDocumentBinding
import com.scribd.app.discover_modules.ModuleViewHolder
import com.scribd.presentation.thumbnail.ThumbnailView
import com.scribd.presentation.view.*
import com.scribd.presentationia.PodcastEpisodeMetadataContract

class DocumentListItemViewHolder(private val binding: ListItemDocumentBinding) : ModuleViewHolder(binding.root),
    PodcastEpisodeMetadataContract.View {
    val thumbnail: ThumbnailView = binding.thumbnail
    val saveForLater: SaveIcon = binding.saveForLaterIv
    val listItemDocumentRestrictions: DocumentRestrictionsView = binding.listItemDocumentRestrictions
    val summaryOfPrefix: TextView = binding.summaryOfPrefix
    val title: TextView = binding.title
    val author: TextView = binding.author
    val uploadedBy: UploadedByView = binding.uploadedBy
    val progressBar: ProgressBar = binding.progress
    val finishedStateView: FinishedStateView = binding.finishedStateView
    val caption: TextView = binding.caption
    val icon: ImageView = binding.listItemIcon
    override val selectionOverlayLayout: View = binding.caption

//    val selectionOverlayLayout: View = binding.listItemOverlayOld.selectionOverlayLayout

    override val viewContext: Context
        get() = binding.root.context

    override fun showPodcastShowTitle(showTitle: String?) {
        author.text = showTitle
        author.visibility = View.VISIBLE
        uploadedBy.visibility = View.GONE
    }

    override fun showRuntime(formattedRunTime: String) {
        caption.visibility = View.VISIBLE
        caption.text = formattedRunTime
    }
}
