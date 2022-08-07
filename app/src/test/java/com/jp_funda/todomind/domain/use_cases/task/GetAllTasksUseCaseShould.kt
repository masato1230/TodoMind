package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.todomind.use_case.task.GetAllTasksUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetAllTasksUseCaseShould {
    private val repository = mock(TaskRepository::class.java)

    @Test
    fun `call getAllTasks function of repository`() = runTest {
        GetAllTasksUseCase(repository)()
        verify(repository, times(1)).getAllTasks()
    }
}