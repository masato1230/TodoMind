package com.jp_funda.todomind.sharedpreference

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationPreference @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "Notification"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getString(key: PreferenceKey): String? {
        return preferences.getString(key.key, null)
    }

    fun setString(key: PreferenceKey, value: String) {
        preferences.edit().putString(key.key, value).apply()
    }
}