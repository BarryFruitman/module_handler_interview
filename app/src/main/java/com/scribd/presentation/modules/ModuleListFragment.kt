package com.scribd.presentation.modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.scribd.presentationia.model.State
import com.scribd.presentationia.modules.ModuleListViewModel
import kotlinx.android.synthetic.main.modules_container.*

abstract class ModuleListFragment : Fragment() {

    val viewModel: ModuleListViewModel by viewModels()

    private lateinit var adapter: ModuleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        adapter = ModuleListAdapter(this, viewModel)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.modules_container, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modulesContainer.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        modulesContainer.adapter = createRecyclerViewAdapter()
        adapter.startOnVisibilityListener(modulesContainer)

        viewModel.contentState.observe(viewLifecycleOwner) { state ->
            contentStateViewWithDefaultBehavior.state = state
            if (state == State.GENERIC_ERROR) {
                onModulesFetchError()
            }
        }

        contentStateViewWithDefaultBehavior.state = State.LOADING
        viewModel.fetchModules()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onVisible()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onHidden(requireActivity().isChangingConfigurations, requireActivity().isFinishing)

        // We only want to reset analytics when the fragment really becomes hidden, and not on device rotation
//        if (viewModel.analyticsLifecycleState == ModuleListViewModel.AnalyticsLifecycleState.READY) {
//            adapter.resetAnalytics()
//        }
    }

    protected open fun onModulesFetchError() = Unit

    protected open fun createRecyclerViewAdapter(): RecyclerView.Adapter<*> = adapter
}

