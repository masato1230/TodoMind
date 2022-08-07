package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.repositories.task.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetNextRemindTaskUseCaseShould {
    private val repository = mock(TaskRepository::class.java)

    @Test
    fun `call getNextRemindTask function of TaskRepository`() = runTest {
        repository.getNextRemindTask(null)
        verify(repository, times(1)).getNextRemindTask(null)
    }
}