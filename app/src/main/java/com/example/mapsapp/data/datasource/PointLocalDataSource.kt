package com.example.mapsapp.data.datasource

import com.example.mapsapp.data.converter.PointConverter
import com.example.mapsapp.data.database.dao.PointDao
import com.example.mapsapp.domain.entity.MapPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface PointLocalDataSource {

    suspend fun get(): Flow<List<MapPoint>>

    suspend fun set(point: MapPoint)
}

class PointLocalDataSourceImpl @Inject constructor(
    private val dao: PointDao,
    private val converter: PointConverter
) : PointLocalDataSource {

    override suspend fun get(): Flow<List<MapPoint>> =
        dao.get().map { it.map(converter::revert) }


    override suspend fun set(point: MapPoint) =
        dao.set(converter.convert(point))
}