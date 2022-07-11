package com.jp_funda.todomind.view.task_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    ogpRepository: OgpRepository,
    settingsPreferences: SettingsPreferences,
) : TaskEditableViewModel(
    ogpRepository = ogpRepository,
    settingsPreferences = settingsPreferences,
)
