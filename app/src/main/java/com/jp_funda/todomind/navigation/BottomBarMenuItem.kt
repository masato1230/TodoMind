package com.jp_funda.todomind.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jp_funda.todomind.R

sealed class BottomBarMenuItem(
    val route: String,
    @StringRes
    val titleRes: Int,
    @DrawableRes
    val iconRes: Int,
) {
    object Top: BottomBarMenuItem(
        route = NavigationRoutes.Top,
        titleRes = R.string.top,
        iconRes = R.drawable.ic_top_24dp,
    )
    object Task: BottomBarMenuItem(
        route = NavigationRoutes.Task,
        titleRes = R.string.task,
        iconRes = R.drawable.ic_todo_24dp,
    )
    object MindMap: BottomBarMenuItem(
        route = NavigationRoutes.MindMap,
        titleRes = R.string.mind_map,
        iconRes = R.drawable.ic_mind_map,
    )
    object Record: BottomBarMenuItem(
        route = NavigationRoutes.Record,
        titleRes = R.string.record,
        iconRes = R.drawable.ic_record_24dp,
    )
    object Settings: BottomBarMenuItem(
        route = NavigationRoutes.Settings,
        titleRes = R.string.settings,
        iconRes = R.drawable.ic_settings_24dp,
    )
}