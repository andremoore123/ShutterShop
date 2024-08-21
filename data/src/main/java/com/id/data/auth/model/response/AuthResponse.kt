package com.id.data.auth.model.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: AuthDataResponse? = null,

    @field:SerializedName("message")
    val message: String? = null
)
