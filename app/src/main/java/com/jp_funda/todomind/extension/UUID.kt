package com.jp_funda.todomind.extension

import java.util.*
import kotlin.math.min

fun UUID.extractFirstFiveDigits(): Int {
    val digitsString = this.toString().replace("[^0-9]".toRegex(), "")
    return digitsString.substring(0, min(5, digitsString.length)).toInt()
}