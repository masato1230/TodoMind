package com.jp_funda.todomind.domain.use_cases.mind_map

import com.google.common.truth.Truth.assertThat
import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.use_case.mind_map.CreateMindMapUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
class CreateMindMapUseCaseShould {
    private val mockedRepository = mock(MindMapRepository::class.java)
    private val fakeMindMap = MindMap(
        title = "title",
        description = "description",
    )

    @Test
    fun `call insertMindMap function of MindMapRepository`() = runTest {
        CreateMindMapUseCase(mockedRepository)(fakeMindMap)

        verify(mockedRepository, times(1)).insertMindMap(fakeMindMap)
    }

    @Test
    fun `set current date to created date field`() = runTest {
        CreateMindMapUseCase(mockedRepository)(fakeMindMap)

        val now = Date()
        assertThat(fakeMindMap.createdDate.time).isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `set current date to updated date field`() = runTest {
        CreateMindMapUseCase(mockedRepository)(fakeMindMap)

        val now = Date()
        assertThat(fakeMindMap.createdDate.time).isIn(Date(now.time - 3000).time..now.time)
    }

    @Test
    fun `set 100 as default value for x`() = runTest {
        fakeMindMap.x = null
        CreateMindMapUseCase(mockedRepository)(fakeMindMap)

        assertEquals(100f, fakeMindMap.x!!)
    }

    @Test
    fun `set 100 as default value for y`() = runTest {
        fakeMindMap.y = null
        CreateMindMapUseCase(mockedRepository)(fakeMindMap)

        assertEquals(100f, fakeMindMap.y!!)
    }
}