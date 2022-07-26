package com.jp_funda.todomind.extension

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.jp_funda.todomind.Constant

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getLeftSlideInTransaction(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(Constant.NAV_ANIM_DURATION),
    )
}

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getLeftSlideOutTransaction(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(Constant.NAV_ANIM_DURATION),
    )
}

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getRightSlideInTransaction(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(Constant.NAV_ANIM_DURATION),
    )
}

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getRightSlideOutTransaction(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(Constant.NAV_ANIM_DURATION),
    )
}