package com.example.mapsapp.util

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

const val INITIAL_LAT = 59.91575028221708
const val INITIAL_LON = 30.303193673240994
const val INITIAL_ZOOM = 14
const val ZOOM_SPEED = 4L

fun MapView.placePoint(latitude: Double, longitude: Double) {
    val marker = Marker(this)
    marker.position = GeoPoint(latitude, longitude)
    this.overlays.add(marker)
    this.invalidate()
}