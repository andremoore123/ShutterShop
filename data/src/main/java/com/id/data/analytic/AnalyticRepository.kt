package com.id.data.analytic

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.id.domain.analytic.IAnalyticRepository
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class AnalyticRepository @Inject constructor(
    private val analytic: FirebaseAnalytics
): IAnalyticRepository {
    override suspend fun logEvent(eventName: String, eventBundle: Bundle) {
        analytic.logEvent(eventName, eventBundle)
    }
}