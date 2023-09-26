package com.example.mapsapp.data.converter

import com.example.mapsapp.data.remote.model.PointRemoteModel
import com.example.mapsapp.domain.entity.MapPoint
import javax.inject.Inject

class PointRemoteConverter @Inject constructor() {

    fun convert(from: PointRemoteModel): MapPoint{
        return MapPoint(
            latitude = from.point.lat,
            longitude = from.point.long
        )
    }
}