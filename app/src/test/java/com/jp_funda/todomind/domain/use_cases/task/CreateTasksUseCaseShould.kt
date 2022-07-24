package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.common.truth.Truth.assertThat
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class CreateTasksUseCaseShould {
    private val repository = mock(TaskRepository::class.java)
    private val getAllTasksUseCase = mock(GetAllTasksUseCase::class.java)
    private val setNextReminderUseCase = mock(SetNextReminderUseCase::class.java)

    private val fakeTasks = (1..100).map { Task(title = it.toString(), reversedOrder = it) }
    private val newTask = Task()

    @Test
    fun `invoke getAllTasksUseCase`() = runTest {
        mockSuccessfulCase()(listOf(newTask))
        verify(getAllTasksUseCase, times(1)).invoke()
    }

    @Test
    fun `set correct reversedOrder`() = runTest {
        mockSuccessfulCase()(listOf(newTask))
        assertEquals(fakeTasks.size + 1, newTask.reversedOrder)
    }

    @Test
    fun `set current date to created date field`() = runTest {
        mockSuccessfulCase()(listOf(newTask))
        val now = Date()
        assertThat(newTask.createdDate.time).isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `set current date to updated date field`() = runTest {
        mockSuccessfulCase()(listOf(newTask))
        val now = Date()
        assertThat(newTask.updatedDate.time).isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `call insertTasks function of TaskRepository`() = runTest {
        mockSuccessfulCase()(listOf(newTask))
        verify(repository, times(1)).insertTasks(listOf(newTask))
    }

    @Test
    fun `invoke setNextReminderUseCase`() = runTest {
        mockSuccessfulCase()(listOf(newTask))
        verify(setNextReminderUseCase, times(1)).invoke()
    }

    private suspend fun mockSuccessfulCase(): CreateTasksUseCase {
        `when`(getAllTasksUseCase.invoke()).thenReturn(fakeTasks)
        return CreateTasksUseCase(
            repository,
            getAllTasksUseCase,
            setNextReminderUseCase,
        )
    }
}