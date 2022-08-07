package com.jp_funda.todomind.view.mind_map_create.options_sheet

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
class AddChildViewModel @Inject constructor() : TaskEditableViewModel() {
    fun setMindMap(mindMap: MindMap) {
        _task.value!!.mindMap = mindMap
    }
}