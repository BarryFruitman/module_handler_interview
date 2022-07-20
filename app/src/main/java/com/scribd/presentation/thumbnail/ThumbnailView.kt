package com.scribd.presentation.thumbnail

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.scribd.dataia.Document
import com.scribd.presentationia.thumbnail.ThumbnailModel

class ThumbnailView(context: Context): ConstraintLayout(context) {
    lateinit var scaleType: ImageView.ScaleType
    var thumbnailWidth: Int = 0
    var thumbnailHeight: Int = 0
    lateinit var document: Document
    var model: ThumbnailModel? = null

    var onLongClickListener: OnLongClickListener? = null
        set(value) {
            field = value
            setOnLongClickListener {
                model?.let {
                    true
                } ?: false
            }
        }

    interface OnLongClickListener {
        fun onLongClick(view: ThumbnailView, docId: Int)
    }
}

