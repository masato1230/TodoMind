package com.jp_funda.todomind.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.top.TopScreen

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun BottomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    val mainViewModel = hiltViewModel<MainViewModel>()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationRoutes.Top,
        modifier = modifier,
    ) {
        /** Top Screen. */
        composable(route = BottomBarMenuItem.Top.route) {
            bottomBarState.value = true
            TopScreen(mainViewModel = mainViewModel)
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