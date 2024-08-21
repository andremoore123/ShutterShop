package com.id.domain.session

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class UserModel(
    val name: String,
    val email: String,
) {
    companion object {
        val emptyModel = UserModel(
            name = "", email = ""
        )
    }
}
