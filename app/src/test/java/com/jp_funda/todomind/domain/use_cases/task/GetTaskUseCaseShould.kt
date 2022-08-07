package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.todomind.use_case.task.GetTaskUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
class GetTaskUseCaseShould {
    private val repository = mock(TaskRepository::class.java)

    private val taskId = UUID.randomUUID()

    @Test
    fun `call getTask function of TaskRepository`() = runTest {
        GetTaskUseCase(repository)(taskId)
        verify(repository, times(1)).getTask(taskId)
    }
}