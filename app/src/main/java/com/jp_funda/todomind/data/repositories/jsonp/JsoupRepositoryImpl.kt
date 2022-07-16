package com.jp_funda.todomind.data.repositories.jsonp

import com.jp_funda.todomind.domain.repositories.JsoupRepository
import com.jp_funda.todomind.util.JsoupUtil.AGENT
import com.jp_funda.todomind.util.JsoupUtil.REFERRER
import com.jp_funda.todomind.util.JsoupUtil.TIMEOUT
import org.jsoup.Connection
import org.jsoup.Jsoup

@Suppress("BlockingMethodInNonBlockingContext")
class JsoupRepositoryImpl : JsoupRepository {
    override suspend fun fetchConnectionResponse(url: String): Connection.Response {
        return Jsoup.connect(url)
            .userAgent(AGENT)
            .referrer(REFERRER)
            .timeout(TIMEOUT)
            .execute()
    }
}