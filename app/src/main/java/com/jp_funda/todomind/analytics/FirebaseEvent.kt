package com.jp_funda.todomind.analytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object FirebaseEvent {
    private val firebaseAnalytics = Firebase.analytics

    fun screenView(screenName: String) {
        val params = bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to screenName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }
}