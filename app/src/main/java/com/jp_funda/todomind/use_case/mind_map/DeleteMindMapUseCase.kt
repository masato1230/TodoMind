package com.jp_funda.todomind.use_case.mind_map

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.use_case.SetNextReminderUseCase
import com.jp_funda.todomind.use_case.task.DeleteTasksInAMindMapUseCase
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class DeleteMindMapUseCase @Inject constructor(
    private val repository: MindMapRepository,
    private val deleteTasksInAMindMapUseCase: DeleteTasksInAMindMapUseCase,
    private val setNextReminderUseCase: SetNextReminderUseCase,
) {
    suspend operator fun invoke(mindMap: MindMap) {
        deleteTasksInAMindMapUseCase(mindMap)
        repository.deleteMindMap(mindMap)
        setNextReminderUseCase()
    }
}