package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.common.truth.Truth.assertThat
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.todomind.use_case.SetNextReminderUseCase
import com.jp_funda.todomind.use_case.task.UpdateTaskUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class UpdateTaskUseCaseShould {
    private val repository = mock(TaskRepository::class.java)
    private val setNextReminderUseCase = mock(SetNextReminderUseCase::class.java)

    private val updatingTask = Task()

    @Test
    fun `set current date to updated date field`() = runTest {
        UpdateTaskUseCase(repository, setNextReminderUseCase)(updatingTask)

        val now = Date()
        assertThat(updatingTask.updatedDate.time).isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `call updateTask function of TaskRepository`() = runTest {
        UpdateTaskUseCase(repository, setNextReminderUseCase)(updatingTask)
        verify(repository, times(1)).updateTask(updatingTask)
    }

    @Test
    fun `invoke setNextReminderUseCase`() = runTest {
        UpdateTaskUseCase(repository, setNextReminderUseCase)(updatingTask)
        verify(setNextReminderUseCase, times(1)).invoke()
    }
}