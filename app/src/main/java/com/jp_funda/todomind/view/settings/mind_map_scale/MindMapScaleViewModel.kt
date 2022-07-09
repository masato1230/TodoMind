package com.jp_funda.todomind.view.settings.mind_map_scale

import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapScaleViewModel @Inject constructor(
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {
    var scale = settingsPreferences.getFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE, 1f)

    override fun onCleared() {
        settingsPreferences.setFloat(PreferenceKeys.DEFAULT_MIND_MAP_SCALE, scale)
    }
}