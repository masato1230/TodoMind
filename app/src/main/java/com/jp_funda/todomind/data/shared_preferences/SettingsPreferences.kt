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

    fun getFloat(key: PreferenceKeys): Float {
        return preferences.getFloat(key.key, -1f)
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