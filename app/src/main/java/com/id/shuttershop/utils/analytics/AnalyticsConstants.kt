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

    // Register Event Names
    const val EVENT_REGISTER_ATTEMPT = "register_attempt"
    const val EVENT_REGISTER_SUCCESS = "register_success"
    const val EVENT_REGISTER_FAILURE = "register_failure"

    // Logout Event Names
    const val EVENT_LOGOUT_ATTEMPT = "logout_attempt"

    // Parameter Profile
    const val LOGOUT_BUTTON = "logout"


    // Transaction Event Names
    const val EVENT_RATE_PRODUCT = "rate_product"

    // Parameter Transaction
    const val PRODUCT_NAME = "product_name"
    const val RATE_BUTTON = "rate"


    // Parameter Names
    const val PARAM_BUTTON = "btn_click"
    const val PARAM_SCREEN_NAME = "screen_name"
    const val PARAM_SCREEN_INDEX = "screen_index"
    const val PARAM_EMAIL = "email"
    const val PARAM_ERROR_MESSAGE = "error_message"
}