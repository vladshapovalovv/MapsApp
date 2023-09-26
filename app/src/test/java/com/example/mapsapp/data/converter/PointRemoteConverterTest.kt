package com.example.mapsapp.data.converter

import com.example.mapsapp.data.remote.model.PointRemoteModel
import com.example.mapsapp.domain.entity.MapPoint
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointRemoteConverterTest {

    private val converter = PointRemoteConverter()

    private val pointRemoteModel = PointRemoteModel(PointRemoteModel.Point(lat = 63.1231, long = 28.12313))
    private val expectedMapPoint = MapPoint(latitude = 63.1231, longitude = 28.12313)

    @Test
    fun `convert PointRemoteModel to MapPoint EXPECT MapPoint`() {
        val actualMapPoint = converter.convert(pointRemoteModel)

        assertEquals(expectedMapPoint, actualMapPoint)
    }
}