package com.id.data.auth.model.request

import androidx.annotation.Keep

@Keep
data class RefreshRequest(
    val token: String
)
