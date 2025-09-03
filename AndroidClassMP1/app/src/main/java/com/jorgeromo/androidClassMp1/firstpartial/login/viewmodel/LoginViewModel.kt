package com.jorgeromo.androidClassMp1.firstpartial.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidClassMp1.firstpartial.login.model.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUIState(isLoading = true)

            try {
                val response = repository.loginUser(email, password)

                if (response.isSuccessful) {
                    val data = response.body()
                    _uiState.value = LoginUIState(
                        isLoading = false,
                        successMessage = "Welcome ${data?.user?.name}!" // ✅ ahora funciona
                    )
                } else {
                    _uiState.value = LoginUIState(
                        isLoading = false,
                        errorMessage = "Error ${response.code()}: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LoginUIState(
                    isLoading = false,
                    errorMessage = "Exception: ${e.message}"
                )
            }
        }
    }
}

