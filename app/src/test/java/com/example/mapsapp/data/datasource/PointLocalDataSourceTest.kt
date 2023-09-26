package com.example.mapsapp.data.datasource

import com.example.mapsapp.data.converter.PointConverter
import com.example.mapsapp.data.database.dao.PointDao
import com.example.mapsapp.data.database.model.PointModel
import com.example.mapsapp.domain.entity.MapPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class PointLocalDataSourceImplTest {

    @Mock
    private lateinit var dao: PointDao

    @Mock
    private lateinit var converter: PointConverter

    @Mock
    private lateinit var dataSource: PointLocalDataSourceImpl

    @Test
    fun `get EXPECT get converted points from dao`() = runTest {
        val mapPoint = MapPoint(id = 0, latitude = 59.1212311, longitude = 33.1231321334)
        val pointModel = PointModel(id = 0, latitude = 59.1212311, longitude = 33.1231321334)
        val pointList = listOf(pointModel)

        whenever(dao.get()).thenReturn(flowOf(pointList))
        whenever(converter.revert(pointModel)).thenReturn(mapPoint)

        dataSource = PointLocalDataSourceImpl(dao, converter)

        dataSource.get().collect {
            val actual = it
            val expected = listOf(mapPoint)
            assertEquals(expected, actual)
        }

    }

    @Test
    fun `set EXPECT convert and set points to dao`() = runTest {
        val mapPoint = MapPoint(id = 0, latitude = 59.1212311, longitude = 33.1231321334)
        val pointModel = PointModel(id = 0, latitude = 59.1212311, longitude = 33.1231321334)

        whenever(converter.convert(mapPoint)).thenReturn(pointModel)

        val dataSource = PointLocalDataSourceImpl(dao, converter)
        dataSource.set(mapPoint)

        verify(converter).convert(mapPoint)
        verify(dao).set(pointModel)
    }

    private fun runTest(block: suspend TestScope.() -> Unit) = kotlinx.coroutines.test.runTest {
        block()
    }
}