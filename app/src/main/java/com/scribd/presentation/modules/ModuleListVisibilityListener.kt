package com.scribd.presentation.modules

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Calls a callback when a position becomes visible.
 * If the item scrolls out of view and back into view, it will call the callback again.
 */
class ModuleListVisibilityListener() {

    // Keep track of the last visible range and don't notify overlaps
    var visibleRange = emptyList<Int>()

    fun startVisibiityListener(recyclerView: RecyclerView, callback: (Int) -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // ModuleListFragment only uses LinearLayoutManager
                (recyclerView.layoutManager as LinearLayoutManager?)?.let { layoutManager ->
                    val first = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val last = layoutManager.findLastCompletelyVisibleItemPosition()

                    val previousRange = visibleRange
                    visibleRange = (first..last).toList()
                    val newlyVisibleRange = visibleRange.minus(previousRange)  // Don't notify the same item twice in a row
                    for (position in newlyVisibleRange) {
                        callback(position)
                    }
                }
            }
        })
    }
}