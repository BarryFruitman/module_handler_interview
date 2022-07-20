package com.scribd.presentationia.thumbnail

import androidx.lifecycle.ViewModel
import com.scribd.app.discover_modules.document_list_item.DocumentListItemViewHolder
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class ThumbnailViewModel : ViewModel() {
    fun onThumbnailLongClick(docId: Int): Boolean {
        return false
    }

    fun resetThumbnail(holder: DocumentListItemViewHolder) {}
    fun setupThumbnail(
        serverId: Int,
        viewLifecycleOwner: LifecycleOwner,
        dataObserver: Observer<in ThumbnailModel?>,
        holder: DocumentListItemViewHolder
    ) {
    }
}