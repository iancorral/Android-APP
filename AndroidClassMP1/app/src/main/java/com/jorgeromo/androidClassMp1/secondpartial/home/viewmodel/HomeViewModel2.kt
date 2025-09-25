package com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidClassMp1.secondpartial.home.model.HomeSection
import com.jorgeromo.androidClassMp1.secondpartial.home.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.io.IOException

// Definimos los posibles estados de nuestra UI
sealed interface HomeUiState {
    data class Success(val sections: List<HomeSection>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel2 : ViewModel() {
    // Estado de la UI, inicializado como Loading
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        // En cuanto el ViewModel se crea, mandamos a llamar los datos
        fetchHomeScreenData()
    }

    private fun fetchHomeScreenData() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading // Aseguramos el estado de carga
            try {
                // Llamada a la red a través de Retrofit
                val response = RetrofitInstance.api.getHomeScreenData()
                homeUiState = HomeUiState.Success(response.homeScreen)
            } catch (e: IOException) {
                // Si hay un error de red o de parsing
                homeUiState = HomeUiState.Error
            }
        }
    }
}