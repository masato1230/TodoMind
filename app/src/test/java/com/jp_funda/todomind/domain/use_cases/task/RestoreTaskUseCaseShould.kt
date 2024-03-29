package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.common.truth.Truth.assertThat
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.todomind.use_case.SetNextReminderUseCase
import com.jp_funda.todomind.use_case.task.RestoreTaskUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
class RestoreTaskUseCaseShould {
    private val repository = mock(TaskRepository::class.java)
    private val setNextReminderUseCase = mock(SetNextReminderUseCase::class.java)

    private val restoringTask = Task()

    @Test
    fun `set current date to updated date field`() = runTest {
        RestoreTaskUseCase(repository, setNextReminderUseCase)(restoringTask)

        val now = Date()
        assertThat(restoringTask.updatedDate.time).isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `call insertTasks function of TaskRepository`() = runTest {
        RestoreTaskUseCase(repository, setNextReminderUseCase)(restoringTask)
        verify(repository, times(1)).insertTasks(listOf(restoringTask))
    }

    @Test
    fun `invoke setNextReminderUseCase`() = runTest {
        RestoreTaskUseCase(repository, setNextReminderUseCase)(restoringTask)
        verify(setNextReminderUseCase, times(1)).invoke()
    }
}