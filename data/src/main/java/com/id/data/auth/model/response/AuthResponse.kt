package com.id.data.auth.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: AuthDataResponse? = null,

    @field:SerializedName("message")
    val message: String? = null
)
