package com.jp_funda.todomind.domain.use_cases.mind_map

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import com.jp_funda.todomind.domain.use_cases.task.DeleteTasksInAMindMapUseCase
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class DeleteMindMapUseCase @Inject constructor(
    private val repository: MindMapRepository,
    private val deleteTasksInAMindMapUseCase: DeleteTasksInAMindMapUseCase,
    private val setNextReminderUseCase: SetNextReminderUseCase,
) {
    suspend operator fun invoke(mindMap: MindMap) {
        repository.deleteMindMap(mindMap)
        deleteTasksInAMindMapUseCase(mindMap)
        setNextReminderUseCase()
    }
}