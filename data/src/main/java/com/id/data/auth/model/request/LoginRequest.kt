package com.id.data.auth.model.request

data class LoginRequest(
    val email: String,
    val password: String,
    val firebaseToken: String,
)
