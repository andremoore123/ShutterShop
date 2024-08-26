package com.id.data.auth.model.request

import androidx.annotation.Keep

@Keep
data class RegisterRequest(
    val email: String,
    val password: String,
    val firebaseToken: String,
)
