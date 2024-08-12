package com.id.domain.auth.model

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class AuthDataModel(
    val userName: String,
    val userImageUrl: String,
    val accessToken: String,
    val refreshToken: String,
)
