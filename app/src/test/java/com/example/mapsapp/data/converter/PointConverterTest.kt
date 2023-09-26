package com.example.mapsapp.data.converter

import com.example.mapsapp.data.database.model.PointModel
import com.example.mapsapp.domain.entity.MapPoint
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointConverterTest {

    private val converter = PointConverter()

    private val mapPoint = MapPoint(latitude = 61.1231321, longitude = 32.3132193)
    private val pointModel = PointModel(latitude = 61.1231321, longitude = 32.3132193)

    @Test
    fun `convert MapPoint to PointModel EXPECT PointModel`() {
        val expected = pointModel
        val actual = converter.convert(mapPoint)

        assertEquals(expected, actual)
    }

    @Test
    fun `revert PointModel to MapPoint EXPECT MapPoint`() {
        val expected = mapPoint
        val actual = converter.revert(pointModel)

        assertEquals(expected, actual)
    }
}