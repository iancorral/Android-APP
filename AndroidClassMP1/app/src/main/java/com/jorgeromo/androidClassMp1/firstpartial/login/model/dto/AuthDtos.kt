package com.jorgeromo.androidClassMp1.firstpartial.login.model.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String,
    val user: User
)

data class User(
    val id: String,
    val name: String,
    val email: String
)
