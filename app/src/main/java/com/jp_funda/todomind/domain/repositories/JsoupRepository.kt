package com.jp_funda.todomind.domain.repositories

import org.jsoup.Connection

interface JsoupRepository {
    suspend fun fetchConnectionResponse(url: String): Connection.Response
}