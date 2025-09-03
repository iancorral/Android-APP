package com.jorgeromo.androidClassMp1.firstpartial.login.model.network

import com.jorgeromo.androidClassMp1.firstpartial.login.model.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

interface AuthApi {
    @POST("api/auth/login") // 👈 asegurate que sea login
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}
