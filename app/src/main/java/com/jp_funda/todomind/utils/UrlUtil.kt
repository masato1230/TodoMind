package com.jp_funda.todomind.utils

object UrlUtil {
    fun extractURLs(text: String): List<String> {
        val regex = "(http://|https://)[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+"
        val urls = regex.toRegex(RegexOption.IGNORE_CASE).findAll(text).map{it.value}
        return urls.toList()
    }
}