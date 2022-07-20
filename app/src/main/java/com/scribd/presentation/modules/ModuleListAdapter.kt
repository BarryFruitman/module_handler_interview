package com.scribd.presentation.modules

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scribd.presentationia.modules.ModuleListViewModel

class ModuleListAdapter(
    fragment: ModuleListFragment,
    private val viewModel: ModuleListViewModel
) : RecyclerView.Adapter<ModuleViewHolder>() {

    private val binders = RegisteredModuleBinders(fragment)

    override fun getItemCount() = viewModel.moduleCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder =
        binders.binderForViewType(viewType).createViewHolder(parent)

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) =
        holder.viewModel.bindModule(viewModel.getModuleAt(position))

    override fun getItemViewType(position: Int) =
        viewModel.getModuleType(position).hashCode()

    fun startOnVisibilityListener(recyclerView: RecyclerView) {
        ModuleListVisibilityListener().startVisibiityListener(recyclerView) { position ->
            val moduleType = viewModel.getModuleType(position)
            val moduleViewModel = binders.binderForModuleType(moduleType)
            moduleViewModel.onModuleVisible()
            viewModel.onModuleVisible(position)
        }
    }
}