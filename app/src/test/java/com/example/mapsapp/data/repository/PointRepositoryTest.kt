package com.example.mapsapp.data.repository

import com.example.mapsapp.data.datasource.PointLocalDataSource
import com.example.mapsapp.domain.entity.MapPoint
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PointRepositoryImplTest {

    @Mock
    private lateinit var localDataSource: PointLocalDataSource

    private lateinit var repository: PointRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = PointRepositoryImpl(localDataSource)
    }

    @Test
    fun `get EXPECT a flow of map points`() = runBlocking {
        val mapPoint = MapPoint(id = 1, latitude = 59.1212311, longitude = 33.1231321334)
        val pointList = listOf(mapPoint)

        `when`(localDataSource.get()).thenReturn(flowOf(pointList))

        val resultFlow = repository.get()

        val actualList = mutableListOf<MapPoint>()
        resultFlow.collect {
            actualList.addAll(it)
        }

        val expectedList = listOf(mapPoint)
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `set EXPECT map point in local data source`() = runBlocking {
        val mapPoint = MapPoint(id = 1, latitude = 59.1212311, longitude = 33.1231321334)

        `when`(localDataSource.set(mapPoint)).thenReturn(Unit)

        repository.set(mapPoint)
    }
}