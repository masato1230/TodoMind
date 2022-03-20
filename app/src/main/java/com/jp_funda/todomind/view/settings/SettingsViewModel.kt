package com.jp_funda.todomind.view.settings

import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsPreferences: SettingsPreferences) :
    ViewModel() {

    val defaultMindMapScale: Float
        get() {
            val scale = settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE)
            return if (scale < 0) 1f else scale
        }

    val isShowOgpThumbnail: Boolean
        get() {
            return settingsPreferences.getBoolean(PreferenceKeys.IS_SHOW_OGP_THUMBNAIL)
        }

    fun setIsShowOgpThumbnail(isShow: Boolean) {
        settingsPreferences.setBoolean(PreferenceKeys.IS_SHOW_OGP_THUMBNAIL, isShow)
    }
}