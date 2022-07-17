package com.jp_funda.todomind.view.task_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
class TaskDetailViewModel @Inject constructor() : TaskEditableViewModel()
