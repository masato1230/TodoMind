package com.jp_funda.todomind.data.shared_preferences

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsPreferences @Inject constructor(context: Context) {
    companion object {
        const val PREF_NAME = "Settings"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getFloat(key: PreferenceKeys, default: Float = -1f): Float {
        return preferences.getFloat(key.key, default)
    }

    fun setFloat(key: PreferenceKeys, value: Float) {
        preferences.edit().putFloat(key.key, value).apply()
    }

    fun getBoolean(key: PreferenceKeys): Boolean {
        return preferences.getBoolean(key.key, false)
    }

    fun setBoolean(key: PreferenceKeys, value: Boolean) {
        preferences.edit().putBoolean(key.key, value).apply()
    }
}