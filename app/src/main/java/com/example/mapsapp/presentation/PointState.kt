package com.example.mapsapp.presentation

import com.example.mapsapp.domain.entity.MapPoint
import kotlinx.coroutines.flow.Flow

sealed interface PointState {

    data class Content(val points: List<MapPoint>) : PointState

    data object Empty : PointState

    data class Error(val message: String) : PointState
}