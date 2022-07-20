package com.scribd.presentation.modules

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import com.scribd.presentationia.modules.ModuleViewModel

abstract class ModuleViewHolder(itemView: View, val viewModel: ModuleViewModel) : RecyclerView.ViewHolder(itemView)