package com.id.data.auth.model.response

import com.id.domain.auth.model.AuthDataModel

data class AuthDataResponse(
    val accessToken: String? = null,
    val expiresAt: Int? = null,
    val refreshToken: String? = null,
    val userName: String? = null,
    val userImage: String? = null
)

fun AuthDataResponse.toModel(): AuthDataModel {
    return AuthDataModel(
        userName = userName ?: "",
        userImageUrl = userImage ?: "",
        accessToken = accessToken ?: "",
        refreshToken = refreshToken ?: ""
    )
}