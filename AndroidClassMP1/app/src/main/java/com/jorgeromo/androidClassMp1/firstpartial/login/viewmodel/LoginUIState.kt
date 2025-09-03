package com.jorgeromo.androidClassMp1.firstpartial.login.viewmodel

data class LoginUIState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
