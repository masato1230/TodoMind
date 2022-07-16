package com.jp_funda.todomind.util

object JsoupUtil {
    const val AGENT = "Mozilla"
    const val REFERRER = "http://www.google.com"
    const val TIMEOUT = 10000
    const val DOC_SELECT_QUERY = "meta[property^=og:]"
    const val OPEN_GRAPH_KEY = "content"
    const val PROPERTY = "property"
    const val OG_IMAGE = "og:image"
    const val OG_DESCRIPTION = "og:description"
    const val OG_URL = "og:url"
    const val OG_TITLE = "og:title"
    const val OG_SITE_NAME = "og:site_name"
    const val OG_TYPE = "og:type"
}