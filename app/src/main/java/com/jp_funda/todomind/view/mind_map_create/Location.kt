package com.jp_funda.todomind.view.mind_map_create

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val x: Float,
    val y: Float,
) : Parcelable
