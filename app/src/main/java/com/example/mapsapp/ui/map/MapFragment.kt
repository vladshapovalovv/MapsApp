package com.example.mapsapp.ui.map

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mapsapp.R
import com.example.mapsapp.databinding.FragmentMapBinding
import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.presentation.MapViewModel
import com.example.mapsapp.presentation.PointState
import com.example.mapsapp.util.INITIAL_LAT
import com.example.mapsapp.util.INITIAL_LON
import com.example.mapsapp.util.INITIAL_ZOOM
import com.example.mapsapp.util.ZOOM_SPEED
import com.example.mapsapp.util.placePoint
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)

    private val viewModel by viewModels<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
        initObservers()
        initListeners()
    }

    private fun initMap() {
        Configuration.getInstance().load(context, activity?.getPreferences(Context.MODE_PRIVATE))
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)

        viewModel.loadPoints()

        val location = GeoPoint(INITIAL_LAT, INITIAL_LON)
        val mapController = binding.mapView.controller

        mapController.apply {
            animateTo(location)
            zoomTo(INITIAL_ZOOM, ZOOM_SPEED)
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun initListeners() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.save -> {
                    val mapCenter = binding.mapView.mapCenter

                    SaveCoordinatesDialog(
                        context = requireContext(),
                        initialLatitude = mapCenter.latitude,
                        initialLongitude = mapCenter.longitude
                    ) { latitude, longitude ->
                        viewModel.savePoint(
                            MapPoint(
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }.show()
                    true
                }

                R.id.show_list -> {
                    findNavController().navigate(R.id.action_mapFragment_to_pointFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun applyState(state: PointState) {
        when (state) {
            is PointState.Content -> applyContentState(state)

            is PointState.Empty -> {}

            is PointState.Error -> applyErrorState(state)
        }
    }

    private fun applyContentState(state: PointState.Content) {
        state.points.forEach { binding.mapView.placePoint(it.latitude, it.longitude) }
    }

    private fun applyErrorState(state: PointState.Error) =
        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()

}