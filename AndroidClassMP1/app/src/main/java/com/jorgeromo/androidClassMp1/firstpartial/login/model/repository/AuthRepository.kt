package com.jorgeromo.androidClassMp1.firstpartial.login.model.repository

import com.jorgeromo.androidClassMp1.firstpartial.login.model.dto.LoginResponse
import com.jorgeromo.androidClassMp1.firstpartial.login.model.network.AuthApi
import com.jorgeromo.androidClassMp1.firstpartial.login.model.network.LoginRequest
import retrofit2.Response

class AuthRepository(private val api: AuthApi) {

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return api.loginUser(LoginRequest(email, password))
    }
}
