package com.jp_funda.todomind.util

object UrlUtil {

    fun extractURLs(text: String): List<String> {
        val regex =
            "[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)\n"
        val urls = regex.toRegex(RegexOption.IGNORE_CASE).findAll(text).map { it.value }
        return urls.toList()
    }
}