package com.jp_funda.todomind.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.extension.getLeftSlideInTransaction
import com.jp_funda.todomind.extension.getLeftSlideOutTransaction
import com.jp_funda.todomind.extension.getRightSlideInTransaction
import com.jp_funda.todomind.extension.getRightSlideOutTransaction
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.mind_map.MindMapScreen
import com.jp_funda.todomind.view.record.RecordScreen
import com.jp_funda.todomind.view.settings.SettingsScreen
import com.jp_funda.todomind.view.task.TaskScreen
import com.jp_funda.todomind.view.task_detail.TaskDetailScreen
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
        composable(route = NavigationRoutes.Top) {
            bottomBarState.value = true
            TopScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }

        /** Task Screen. */
        composable(route = NavigationRoutes.Task) {
            bottomBarState.value = true
            TaskScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }

        /** MindMap Screen. */
        composable(route = NavigationRoutes.MindMap) {
            bottomBarState.value = true
            MindMapScreen(mainViewModel = mainViewModel)
        }

        /** Record Screen. */
        composable(route = NavigationRoutes.Record) {
            bottomBarState.value = true
            RecordScreen()
        }

        /** Settings Screen. */
        composable(route = NavigationRoutes.Settings) {
            bottomBarState.value = true
            SettingsScreen()
        }

        /** TaskDetail Screen. */
        composable(
            route = NavigationRoutes.TaskDetail,
            enterTransition = { getLeftSlideInTransaction() },
            exitTransition = { getLeftSlideOutTransaction() },
            popEnterTransition = { getRightSlideInTransaction() },
            popExitTransition = { getRightSlideOutTransaction() },
        ) {
            bottomBarState.value = false
            TaskDetailScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }
    }
}