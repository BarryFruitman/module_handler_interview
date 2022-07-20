package com.scribd.presentation.modules.binders.document_carousel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.scribd.presentation.thumbnail.ThumbnailView
import com.scribd.presentationia.thumbnail.ThumbnailModel

class CarouselAdapter: RecyclerView.Adapter<CarouselAdapter.ThumbnailViewHolder>() {
    var thumbnailModels: List<ThumbnailModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ThumbnailViewHolder(root: View): RecyclerView.ViewHolder(root) {
        val thumbnail = root.findViewById<ThumbnailView>(R.id.itemThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ThumbnailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.carousel_thumbnail_new,
                parent,
                false)
        )

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.thumbnail.model = thumbnailModels[position]
    }

    override fun getItemCount() = thumbnailModels.size

//    override fun getNumHeaders() = 0
//
//    override fun getNumFooters() = 0
//
//    override fun logAnalyticsViewEvent(dataIndex: Int) = Unit
}