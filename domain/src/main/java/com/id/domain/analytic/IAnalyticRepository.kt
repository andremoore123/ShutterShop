package com.id.domain.analytic

import android.os.Bundle

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IAnalyticRepository {
    fun logEvent(eventName: String, eventBundle: Bundle)
}