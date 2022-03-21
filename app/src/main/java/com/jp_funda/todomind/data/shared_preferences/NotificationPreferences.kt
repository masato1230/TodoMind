package com.jp_funda.todomind.data.shared_preferences

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationPreferences @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "Notification"
    }
    
    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    
    fun getString(key: PreferenceKeys): String {
        return preferences.getString()
    }
}