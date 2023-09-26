package com.example.mapsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.domain.usecase.GetMapPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val getMapPointsUseCase: GetMapPointsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PointState>()
    val state: LiveData<PointState> = _state

    fun loadPoints() {
        viewModelScope.launch(handleError + Dispatchers.IO) {

            getMapPointsUseCase.invoke().collect { collectedPoints ->

                withContext(Dispatchers.Main) {
                    if (collectedPoints.isNotEmpty()) {
                        _state.value = PointState.Content(collectedPoints)
                    } else {
                        _state.value = PointState.Empty
                    }
                }
            }
        }
    }

    private val handleError = CoroutineExceptionHandler { _, exception ->
        _state.value = PointState.Error(exception.message ?: exception.toString())
    }

}