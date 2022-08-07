package com.jp_funda.repositories.jsoup

import com.jp_funda.repositories.jsoup.JsoupUtil.AGENT
import com.jp_funda.repositories.jsoup.JsoupUtil.REFERRER
import com.jp_funda.repositories.jsoup.JsoupUtil.TIMEOUT
import com.jp_funda.repositories.jsoup.entity.OpenGraphResult
import org.jsoup.Connection
import org.jsoup.Jsoup

@Suppress("BlockingMethodInNonBlockingContext")
class JsoupRepositoryImpl : JsoupRepository {
    override suspend fun fetchConnectionResponse(url: String): OpenGraphResult {
        val connectionResponse = Jsoup.connect(url)
            .userAgent(AGENT)
            .referrer(REFERRER)
            .timeout(TIMEOUT)
            .execute()

        val openGraphResult = OpenGraphResult()

        val ogpTags = connectionResponse.parse().select(JsoupUtil.DOC_SELECT_QUERY)
        ogpTags.forEach { tag ->
            when (tag.attr(JsoupUtil.PROPERTY)) {
                JsoupUtil.OG_IMAGE -> openGraphResult.image = tag.attr(JsoupUtil.OPEN_GRAPH_KEY)
                JsoupUtil.OG_DESCRIPTION -> openGraphResult.description = tag.attr(JsoupUtil.OPEN_GRAPH_KEY)
                JsoupUtil.OG_URL -> openGraphResult.url = tag.attr(JsoupUtil.OPEN_GRAPH_KEY)
                JsoupUtil.OG_TITLE -> openGraphResult.title = tag.attr(JsoupUtil.OPEN_GRAPH_KEY)
                JsoupUtil.OG_SITE_NAME -> openGraphResult.siteName = tag.attr(JsoupUtil.OPEN_GRAPH_KEY)
                JsoupUtil.OG_TYPE -> openGraphResult.type = tag.attr(JsoupUtil.OPEN_GRAPH_KEY)
            }
        }
        return openGraphResult
    }
}