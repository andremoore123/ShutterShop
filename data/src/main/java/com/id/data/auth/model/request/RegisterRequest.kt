package com.id.data.auth.model.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val firebaseToken: String,
)
