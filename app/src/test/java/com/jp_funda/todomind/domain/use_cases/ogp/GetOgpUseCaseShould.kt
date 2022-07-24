package com.jp_funda.todomind.domain.use_cases.ogp

import com.jp_funda.todomind.domain.repositories.JsoupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetOgpUseCaseShould {
    private val fakeUrl = "example.com"
    private val fakeJsoupRepository = mock(JsoupRepository::class.java)

    @Test
    fun `call fetchJsoupRepository when invoked`() = runTest {
        // Use error case because mocking success Connection.Response from repository is too hard
        val useCase = mockErrorCase()
        try {
            useCase(fakeUrl)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        verify(fakeJsoupRepository, times(1)).fetchConnectionResponse(fakeUrl)
    }

    @Test
    fun `return null when error occur on fetching ogp tags`() = runTest {
        val useCase = mockErrorCase()
        assertEquals(null, useCase(fakeUrl))
    }

    private suspend fun mockErrorCase(): GetOgpUseCase {
        `when`(fakeJsoupRepository.fetchConnectionResponse(fakeUrl)).thenThrow()
        return GetOgpUseCase(fakeJsoupRepository)
    }
}