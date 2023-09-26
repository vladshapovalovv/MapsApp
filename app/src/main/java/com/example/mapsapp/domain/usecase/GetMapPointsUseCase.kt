package com.example.mapsapp.domain.usecase

import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMapPointsUseCase @Inject constructor(
    private val repository: PointRepository
) : suspend () -> Flow<List<MapPoint>> by repository::get