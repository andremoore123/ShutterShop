package com.id.data.auth.model.request

import androidx.annotation.Keep

@Keep
data class LoginRequest(
    val email: String,
    val password: String,
    val firebaseToken: String,
)
