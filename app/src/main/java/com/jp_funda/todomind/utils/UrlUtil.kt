package com.jp_funda.todomind.utils

import org.jsoup.Jsoup

object UrlUtil {
    private const val AGENT = "Mozilla"
    private const val REFERRER = "http://www.google.com"
    private const val TIMEOUT = 10000
    private const val DOC_SELECT_QUERY = "meta[property^=og:]"
    private const val OPEN_GRAPH_KEY = "content"
    private const val PROPERTY = "property"
    private const val OG_IMAGE = "og:image"
    private const val OG_DESCRIPTION = "og:description"
    private const val OG_URL = "og:url"
    private const val OG_TITLE = "og:title"
    private const val OG_SITE_NAME = "og:site_name"
    private const val OG_TYPE = "og:type"

    fun extractURLs(text: String): List<String> {
        val regex =
            "[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)\n"
        val urls = regex.toRegex(RegexOption.IGNORE_CASE).findAll(text).map { it.value }
        return urls.toList()
    }

    fun fetchOGP(siteUrl: String) {
        // Sort out the url
        var url = siteUrl
        if (!url.contains("http")) {
            url = "http://$url"
        }

        val openGraphResult = OpenGraphResult()
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}