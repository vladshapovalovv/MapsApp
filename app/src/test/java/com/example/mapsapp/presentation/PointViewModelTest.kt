package com.example.mapsapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.domain.usecase.GetMapPointsUseCase
import com.example.mapsapp.util.InstantTaskExecutorExtension
import com.example.mapsapp.util.TestCoroutineExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class PointViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    private val getMapPointsUseCase: GetMapPointsUseCase = mock()

    private var viewModel = PointViewModel(getMapPointsUseCase)

    private val stateObserver: Observer<PointState> = mock()

    @Test
    fun `loadPoints with non-empty list EXPECT content state`() = runTest {
        val mockPoints = flowOf(listOf(MapPoint(id = 1, latitude = 59.121, longitude = 33.123)))
        whenever(getMapPointsUseCase.invoke()) doReturn mockPoints

        viewModel.loadPoints()
        viewModel.state.observeForever(stateObserver)

        mockPoints.collect {
            verify(stateObserver).onChanged(PointState.Content(it))
        }
    }

    @Test
    fun `loadPoints with empty list EXPECT empty state`() = runTest {
        val mockPoints = flowOf(emptyList<MapPoint>())
        whenever(getMapPointsUseCase.invoke()) doReturn mockPoints

        viewModel.loadPoints()

        viewModel.state.observeForever(stateObserver)
        mockPoints.collect {
            verify(stateObserver).onChanged(PointState.Empty)
        }
    }

    @Test
    fun `loadPoints with error EXPECT error state`() = runTest {
        val errorMessage = "Couldn't load data"
        whenever(getMapPointsUseCase.invoke()) doThrow IllegalStateException(errorMessage)

        viewModel.loadPoints()
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(PointState.Error(errorMessage))
    }

}