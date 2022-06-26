package com.jp_funda.todomind.extension

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getLeftSlideInTransaction(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(700),
    )
}

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getLeftSlideOutTransaction(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(700),
    )
}

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getRightSlideInTransaction(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(700),
    )
}

@ExperimentalAnimationApi
fun AnimatedContentScope<NavBackStackEntry>.getRightSlideOutTransaction(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(700),
    )
}