package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.use_case.mind_map.GetAllMindMapsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetAllMindMapsUseCaseShould {
    private val mockedRepository = mock(MindMapRepository::class.java)

    @Test
    fun `call getAllMindMaps function of MindMapRepository`() = runTest {
        GetAllMindMapsUseCase(mockedRepository)()
        verify(mockedRepository, times(1)).getAllMindMaps()
    }
}