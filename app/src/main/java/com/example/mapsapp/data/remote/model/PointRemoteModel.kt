package com.example.mapsapp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PointRemoteModel(
    val point: Point
) {
    @Serializable
    data class Point(
        val lat: Double,
        val long: Double
    )
}
