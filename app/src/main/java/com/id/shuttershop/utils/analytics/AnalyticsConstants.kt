package com.id.shuttershop.utils.analytics

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
object AnalyticsConstants {
    // Onboard Event Names
    const val EVENT_ONBOARD_SKIP = "onboard_skip"
    const val EVENT_ONBOARD_NEXT = "onboard_next"

    // Login Event Names
    const val EVENT_LOGIN_ATTEMPT = "login_attempt"
    const val EVENT_LOGIN_SUCCESS = "login_success"
    const val EVENT_LOGIN_FAILURE = "login_failure"

    // Parameter Names
    const val PARAM_SCREEN_NAME = "screen_name"
    const val PARAM_SCREEN_INDEX = "screen_index"
    const val PARAM_EMAIL = "email"
    const val PARAM_ERROR_MESSAGE = "error_message"
}