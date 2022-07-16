package com.jp_funda.todomind.domain.use_cases.ogp

import com.jp_funda.todomind.domain.repositories.JsoupRepository
import com.jp_funda.todomind.domain.use_cases.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.util.JsoupUtil.DOC_SELECT_QUERY
import com.jp_funda.todomind.util.JsoupUtil.OG_DESCRIPTION
import com.jp_funda.todomind.util.JsoupUtil.OG_IMAGE
import com.jp_funda.todomind.util.JsoupUtil.OG_SITE_NAME
import com.jp_funda.todomind.util.JsoupUtil.OG_TITLE
import com.jp_funda.todomind.util.JsoupUtil.OG_TYPE
import com.jp_funda.todomind.util.JsoupUtil.OG_URL
import com.jp_funda.todomind.util.JsoupUtil.OPEN_GRAPH_KEY
import com.jp_funda.todomind.util.JsoupUtil.PROPERTY
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class GetOgpUseCase @Inject constructor(private val repository: JsoupRepository) {
    suspend operator fun invoke(url: String): OpenGraphResult? {
        try {
            val fullLink = if (url.contains("http")) url else "http://${url}"
            val connectionResponse = repository.fetchConnectionResponse(fullLink)

            val openGraphResult = OpenGraphResult()

            val ogpTags = connectionResponse.parse().select(DOC_SELECT_QUERY)
            ogpTags.forEach { tag ->
                when (tag.attr(PROPERTY)) {
                    OG_IMAGE -> openGraphResult.image = tag.attr(OPEN_GRAPH_KEY)
                    OG_DESCRIPTION -> openGraphResult.description = tag.attr(OPEN_GRAPH_KEY)
                    OG_URL -> openGraphResult.url = tag.attr(OPEN_GRAPH_KEY)
                    OG_TITLE -> openGraphResult.title = tag.attr(OPEN_GRAPH_KEY)
                    OG_SITE_NAME -> openGraphResult.siteName = tag.attr(OPEN_GRAPH_KEY)
                    OG_TYPE -> openGraphResult.type = tag.attr(OPEN_GRAPH_KEY)
                }
            }
            return openGraphResult
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}