package com.scribd.app.discover_modules.article

import android.view.View
import com.example.myapplication.R
import com.scribd.app.discover_modules.ModuleViewHolder
import com.scribd.app.ui.ArticleListItemView

class ArticleListItemViewHolder(itemView: View) : ModuleViewHolder(itemView) {
    val articleListItemView: ArticleListItemView = itemView.findViewById(R.id.articleListItemView)
}
