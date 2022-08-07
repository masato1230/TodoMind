package com.jp_funda.todomind.use_case.ogp

import com.jp_funda.repositories.jsoup.JsoupRepository
import com.jp_funda.repositories.jsoup.entity.OpenGraphResult
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class GetOgpUseCase @Inject constructor(private val repository: JsoupRepository) {
    suspend operator fun invoke(url: String): OpenGraphResult? {
        try {
            val fullLink = if (url.contains("http")) url else "http://${url}"
            return repository.fetchConnectionResponse(fullLink)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}