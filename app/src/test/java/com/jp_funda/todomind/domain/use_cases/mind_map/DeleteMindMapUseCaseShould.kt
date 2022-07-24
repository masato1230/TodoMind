package com.jp_funda.todomind.domain.use_cases.mind_map

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import com.jp_funda.todomind.domain.use_cases.task.DeleteTasksInAMindMapUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class DeleteMindMapUseCaseShould {
    private val mockedRepository = mock(MindMapRepository::class.java)
    private val mockedDeleteTasksInAMindMapUseCase = mock(DeleteTasksInAMindMapUseCase::class.java)
    private val mockedSetNextReminderUseCase = mock(SetNextReminderUseCase::class.java)
    private val fakeMindMap = MindMap()

    @Test
    fun `invoke deleteTasksInMindMapUseCase`() = runTest {
        invokeDeleteMindMapUseCase()
        verify(mockedDeleteTasksInAMindMapUseCase, times(1)).invoke(fakeMindMap)
    }

    @Test
    fun `call deleteMindMap of MindMapRepository`() = runTest {
        invokeDeleteMindMapUseCase()
        verify(mockedRepository, times(1)).deleteMindMap(fakeMindMap)
    }

    @Test
    fun `invoke setReminderUseCase`() = runTest {
        invokeDeleteMindMapUseCase()
        verify(mockedSetNextReminderUseCase, times(1)).invoke()
    }

    private suspend fun invokeDeleteMindMapUseCase() {
        DeleteMindMapUseCase(
            mockedRepository,
            mockedDeleteTasksInAMindMapUseCase,
            mockedSetNextReminderUseCase,
        )(fakeMindMap)
    }
}