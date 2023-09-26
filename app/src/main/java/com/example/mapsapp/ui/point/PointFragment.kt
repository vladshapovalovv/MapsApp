package com.example.mapsapp.ui.point

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mapsapp.R
import com.example.mapsapp.databinding.FragmentPointBinding
import com.example.mapsapp.presentation.PointState
import com.example.mapsapp.presentation.PointViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PointFragment : Fragment(R.layout.fragment_point) {

    private val binding by viewBinding(FragmentPointBinding::bind)

    private val viewModel by viewModels<PointViewModel>()

    private var adapter: PointAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        initObservers()
        initListeners()
    }

    private fun initAdapters() {
        adapter = PointAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        viewModel.loadPoints()
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun applyState(state: PointState) {
        when (state) {
            is PointState.Content -> applyContentState(state)

            is PointState.Empty -> applyEmptyState()

            is PointState.Error -> applyErrorState(state)
        }
    }

    private fun applyContentState(state: PointState.Content) {
        binding.recyclerView.isVisible = true
        binding.messageTextView.isGone = true

        adapter?.updateItems(state.points)
    }

    private fun applyEmptyState() {
        binding.messageTextView.isVisible = true
        binding.recyclerView.isVisible = false
        binding.messageTextView.text = getString(R.string.dots_list_empty)

    }

    private fun applyErrorState(state: PointState.Error) {
        binding.messageTextView.isVisible = true
        binding.recyclerView.isVisible = false
        binding.messageTextView.text = state.message

    }

    override fun onDestroy() {
        binding.recyclerView.adapter = null
        super.onDestroy()
    }
}