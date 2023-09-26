package com.example.mapsapp.domain.usecase

import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.domain.repository.PointRepository
import javax.inject.Inject

class SaveMapPointUseCase @Inject constructor(
    private val repository: PointRepository
) : suspend (MapPoint) -> Unit by repository::set