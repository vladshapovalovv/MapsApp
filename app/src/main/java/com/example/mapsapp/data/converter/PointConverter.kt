package com.example.mapsapp.data.converter

import com.example.mapsapp.data.database.model.PointModel
import com.example.mapsapp.domain.entity.MapPoint
import javax.inject.Inject

class PointConverter @Inject constructor() {

    fun convert(from: MapPoint): PointModel {
        return PointModel(
            latitude = from.latitude,
            longitude = from.longitude,
        )
    }

    fun revert(from: PointModel): MapPoint{
        return MapPoint(
            latitude = from.latitude,
            longitude = from.longitude,
            id = from.id
        )
    }
}