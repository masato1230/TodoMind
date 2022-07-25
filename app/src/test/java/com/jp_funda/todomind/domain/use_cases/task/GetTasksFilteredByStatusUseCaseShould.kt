package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.domain.repositories.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetTasksFilteredByStatusUseCaseShould {
    private val repository = mock(TaskRepository::class.java)

    @Test
    fun `call getTasksFilteredByStatus function of TaskRepository`() = runTest {
        GetTasksFilteredByStatusUseCase(repository)(TaskStatus.Open)
        verify(repository, times(1)).getTasksFilteredByStatus(TaskStatus.Open)
    }
}