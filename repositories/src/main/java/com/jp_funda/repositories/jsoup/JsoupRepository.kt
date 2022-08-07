package com.jp_funda.repositories.jsoup

import com.jp_funda.repositories.jsoup.entity.OpenGraphResult

interface JsoupRepository {
    suspend fun fetchConnectionResponse(url: String): OpenGraphResult
}