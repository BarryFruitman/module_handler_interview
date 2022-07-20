package com.scribd.presentationia

import android.content.Context
import com.scribd.app.discover_modules.document_list_item.DocumentListItemViewHolder
import com.scribd.dataia.Document

interface PodcastEpisodeMetadataContract {
    interface View {
        val selectionOverlayLayout: android.view.View
        val viewContext: Context
        fun showPodcastShowTitle(showTitle: String?)
        fun showRuntime(formattedRunTime: String)
    }

    interface Presenter {
        fun setupMetadata(document: Document?)
        fun updateRuntime(document: Document)

        var view: DocumentListItemViewHolder
    }

}
