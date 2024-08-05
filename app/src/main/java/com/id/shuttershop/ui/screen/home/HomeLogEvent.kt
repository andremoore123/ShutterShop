package com.id.shuttershop.ui.screen.home

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class HomeLogEvent(
    val logSearchButton: () -> Unit = {},
    val logNotificationButton: () -> Unit = {},
    val logCartButton: () -> Unit = {}
)
