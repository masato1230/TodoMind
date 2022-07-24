package com.jp_funda.todomind.domain.use_cases.mind_map

import com.google.common.truth.Truth.assertThat
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
class UpdateMindMapUseCaseShould {
    private val mockedRepository = mock(MindMapRepository::class.java)
    private val fakeMindMap = MindMap()

    @Test
    fun `set current date to created date`() = runTest {
        UpdateMindMapUseCase(mockedRepository)(fakeMindMap)

        val now = Date()
        assertThat(fakeMindMap.createdDate.time)
            .isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `call updateMindMap function of MindMapRepository`() = runTest {
        UpdateMindMapUseCase(mockedRepository)(fakeMindMap)
        verify(mockedRepository, times(1)).updateMindMap(fakeMindMap)
    }
}