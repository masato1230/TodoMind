package com.jp_funda.todomind.view.settings.mind_map_scale

import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.sharedpreference.PreferenceKey
import com.jp_funda.todomind.sharedpreference.SettingsPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapScaleViewModel @Inject constructor(
    private val settingsPreference: SettingsPreference,
) : ViewModel() {
    var scale = settingsPreference.getFloat(PreferenceKey.DEFAULT_MIND_MAP_SCALE, 1f)

    override fun onCleared() {
        settingsPreference.setFloat(PreferenceKey.DEFAULT_MIND_MAP_SCALE, scale)
    }
}