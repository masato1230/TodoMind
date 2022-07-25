package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
class DeleteTaskUseCaseShould {
    private val repository = mock(TaskRepository::class.java)
    private val setNextReminderUseCase = mock(SetNextReminderUseCase::class.java)

    private val task = Task()

    @Test
    fun `call deleteTask function of TaskRepository`() = runTest {
        DeleteTaskUseCase(repository, setNextReminderUseCase)(task)
        verify(repository, times(1)).deleteTask(task)
    }

    @Test
    fun `invoke setNextReminderUseCase`() = runTest {
        DeleteTaskUseCase(repository, setNextReminderUseCase)(task)
        verify(setNextReminderUseCase, times(1)).invoke()
    }
}