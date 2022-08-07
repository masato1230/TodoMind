package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.todomind.use_case.task.GetTasksInAMindMapUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetTasksInAMindMapUseCaseShould {
    private val repository = mock(TaskRepository::class.java)

    private val mindMap = MindMap()

    @Test
    fun `call getTasksInAMindMap function of TaskRepository`() = runTest {
        GetTasksInAMindMapUseCase(repository)(mindMap)
        verify(repository, times(1)).getTasksInAMindMap(mindMap)
    }
}