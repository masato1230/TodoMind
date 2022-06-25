package com.jp_funda.todomind.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationRoutes.Top
    ) {
        /** Home Screen. */
        composable(route = BottomBarMenuItem.Top.route) {
            bottomBarState.value = true
            // TODO
            Text(text = BottomBarMenuItem.Top.route)
        }

        /** Task Screen. */
        composable(route = BottomBarMenuItem.Task.route) {
            bottomBarState.value = true
            // TODO
            Text(text = BottomBarMenuItem.Task.route)
        }

        /** MindMap Screen. */
        composable(route = BottomBarMenuItem.MindMap.route) {
            bottomBarState.value = true
            // TODO
            Text(text = BottomBarMenuItem.MindMap.route)
        }

        /** Record Screen. */
        composable(route = BottomBarMenuItem.Record.route) {
            bottomBarState.value = true
            // TODO
            Text(text = BottomBarMenuItem.Record.route)
        }

        /** Settings Screen. */
        composable(route = BottomBarMenuItem.Settings.route) {
            bottomBarState.value = true
            // TODO
            Text(text = BottomBarMenuItem.Settings.route)
        }
    }
}