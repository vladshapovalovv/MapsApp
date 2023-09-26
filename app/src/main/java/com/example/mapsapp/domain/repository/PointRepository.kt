package com.example.mapsapp.domain.repository

import com.example.mapsapp.domain.entity.MapPoint
import kotlinx.coroutines.flow.Flow

interface PointRepository {

    suspend fun get(): Flow<List<MapPoint>>

    suspend fun set(point: MapPoint)

}