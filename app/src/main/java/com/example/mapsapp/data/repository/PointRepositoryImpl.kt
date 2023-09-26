package com.example.mapsapp.data.repository

import com.example.mapsapp.data.datasource.PointLocalDataSource
import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val localDataSource: PointLocalDataSource
) : PointRepository {

    override suspend fun get(): Flow<List<MapPoint>> =
        localDataSource.get()

    override suspend fun set(point: MapPoint) =
        localDataSource.set(point)
}