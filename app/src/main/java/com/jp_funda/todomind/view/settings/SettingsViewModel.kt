package com.jp_funda.todomind.view.settings

import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.sharedpreference.PreferenceKey
import com.jp_funda.todomind.sharedpreference.SettingsPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsPreference: SettingsPreference) :
    ViewModel() {

    val defaultMindMapScale: Float
        get() {
            return settingsPreference.getFloat(PreferenceKey.DEFAULT_MIND_MAP_SCALE, 1f)
        }

    val isShowOgpThumbnail: Boolean
        get() {
            return settingsPreference.getBoolean(PreferenceKey.IS_SHOW_OGP_THUMBNAIL)
        }

    val isRemindTaskDeadline: Boolean
        get() {
            return settingsPreference.getBoolean(PreferenceKey.IS_REMIND_TASK_DEADLINE)
        }

    fun setIsShowOgpThumbnail(isShow: Boolean) {
        settingsPreference.setBoolean(PreferenceKey.IS_SHOW_OGP_THUMBNAIL, isShow)
    }

    fun setIsRemindTaskDeadline(isRemind: Boolean) {
        settingsPreference.setBoolean(PreferenceKey.IS_REMIND_TASK_DEADLINE, isRemind)
    }

    // snackbar
    // Show Undo delete Snackbar
    suspend fun showUndoDeleteSnackbar(
        snackbarHostState: SnackbarHostState
    ) {
        snackbarHostState.showSnackbar(
            message = "This feature is under construction",
            actionLabel = "OK"
        )
    }
}