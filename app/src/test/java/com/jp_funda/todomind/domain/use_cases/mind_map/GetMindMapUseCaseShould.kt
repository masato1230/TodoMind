package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.use_case.mind_map.GetMindMapUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
class GetMindMapUseCaseShould {
    private val mockedRepository = mock(MindMapRepository::class.java)
    private val fakeMindMapId = UUID.randomUUID()

    @Test
    fun `call getMindMap function of MindMapRepository`() = runTest {
        GetMindMapUseCase(mockedRepository)(fakeMindMapId)
        verify(mockedRepository, times(1)).getMindMap(fakeMindMapId)
    }
}