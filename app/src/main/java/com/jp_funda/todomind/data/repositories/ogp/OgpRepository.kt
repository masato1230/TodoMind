package com.jp_funda.todomind.data.repositories.ogp

import com.jp_funda.todomind.domain.use_cases.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.util.JsoupUtil.AGENT
import com.jp_funda.todomind.util.JsoupUtil.DOC_SELECT_QUERY
import com.jp_funda.todomind.util.JsoupUtil.OG_DESCRIPTION
import com.jp_funda.todomind.util.JsoupUtil.OG_IMAGE
import com.jp_funda.todomind.util.JsoupUtil.OG_SITE_NAME
import com.jp_funda.todomind.util.JsoupUtil.OG_TITLE
import com.jp_funda.todomind.util.JsoupUtil.OG_TYPE
import com.jp_funda.todomind.util.JsoupUtil.OG_URL
import com.jp_funda.todomind.util.JsoupUtil.OPEN_GRAPH_KEY
import com.jp_funda.todomind.util.JsoupUtil.PROPERTY
import com.jp_funda.todomind.util.JsoupUtil.REFERRER
import com.jp_funda.todomind.util.JsoupUtil.TIMEOUT
import io.reactivex.rxjava3.core.Single
import org.jsoup.Jsoup
import javax.inject.Inject

class OgpRepository @Inject constructor() {

    fun fetchOgp(siteUrl: String): Single<OpenGraphResult> {
        // Sort out the url
        var url = siteUrl
        if (!url.contains("http")) {
            url = "http://$url"
        }
        val openGraphResult = OpenGraphResult()

        return Single.create { emitter ->
            try {
                val response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent(AGENT)
                    .referrer(REFERRER)
                    .timeout(TIMEOUT)
                    .followRedirects(true)
                    .execute()
                val doc = response.parse()
                val ogTags = doc.select(DOC_SELECT_QUERY)
                when {
                    ogTags.size > 0 ->
                        ogTags.forEachIndexed { index, _ ->
                            val tag = ogTags[index]
                            when (tag.attr(PROPERTY)) {
                                OG_IMAGE -> {
                                    openGraphResult.image = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_DESCRIPTION -> {
                                    openGraphResult.description = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_URL -> {
                                    openGraphResult.url = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_TITLE -> {
                                    openGraphResult.title = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_SITE_NAME -> {
                                    openGraphResult.siteName = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_TYPE -> {
                                    openGraphResult.type = (tag.attr(OPEN_GRAPH_KEY))
                                }
                            }
                        }
                }
            } catch (e: Throwable) {
                emitter.onError(e)
            }
            emitter.onSuccess(openGraphResult)
        }
    }
}