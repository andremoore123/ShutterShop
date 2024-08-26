package com.id.shuttershop.ui.screen.auth.register

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class RegisterEvent(
    val onNameChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onRegisterClick: () -> Unit = {},
    val onLoginClick: () -> Unit = {},
    val onEntriesChange: (String, String, String) -> Boolean = { _, _, _ -> false }
)
