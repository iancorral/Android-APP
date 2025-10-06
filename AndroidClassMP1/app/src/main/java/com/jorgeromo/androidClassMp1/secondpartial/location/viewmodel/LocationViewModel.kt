package com.jorgeromo.androidClassMp1.secondpartial.location.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidClassMp1.secondpartial.location.repository.LocationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LocationUiState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isUpdating: Boolean = false,
    val error: String? = null
)

class LocationViewModel(private val repo: LocationRepository) : ViewModel() {

    private val _ui = MutableStateFlow(LocationUiState())
    val ui: StateFlow<LocationUiState> = _ui

    private var updatesJob: Job? = null

    fun startUpdates() {
        if (_ui.value.isUpdating) return
        _ui.value = _ui.value.copy(isUpdating = true, error = null)
        updatesJob = viewModelScope.launch {
            try {
                repo.locationUpdates().collect { loc: Location ->
                    _ui.value = _ui.value.copy(
                        latitude = loc.latitude,
                        longitude = loc.longitude
                    )
                }
            } catch (e: Exception) {
                _ui.value = _ui.value.copy(error = e.message, isUpdating = false)
            }
        }
    }

    fun stopUpdates() {
        updatesJob?.cancel()
        updatesJob = null
        _ui.value = _ui.value.copy(isUpdating = false)
    }

    fun onPermissionGranted() = startUpdates()

    override fun onCleared() {
        super.onCleared()
        stopUpdates()
    }
}