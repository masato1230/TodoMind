package com.jp_funda.todomind.view

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.navigation.BottomBarMenuItem
import com.jp_funda.todomind.navigation.BottomNavGraph
import com.jp_funda.todomind.navigation.NavigationRoutes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    val bottomBarState = remember { mutableStateOf(true) }

    Scaffold(bottomBar = {
        if (bottomBarState.value) {
            BottomBar(navController = navController)
        }
    }) {
        BottomNavGraph(navController, bottomBarState)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val menuItems = listOf(
        BottomBarMenuItem.Top,
        BottomBarMenuItem.Task,
        BottomBarMenuItem.MindMap,
        BottomBarMenuItem.Record,
        BottomBarMenuItem.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = colorResource(id = R.color.deep_purple)) {
        menuItems.forEach { menuItem ->
            AddItem(
                menuItem = menuItem,
                currentDestination = currentDestination,
                navController = navController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    menuItem: BottomBarMenuItem,
    currentDestination: NavDestination?,
    navController: NavController,
) {
    BottomNavigationItem(
        label = { Text(text = stringResource(id = menuItem.titleRes)) },
        icon = {
            Icon(
                painter = painterResource(id = menuItem.iconRes),
                contentDescription = "navigation icon",
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == menuItem.route } == true,
        onClick = {
            if (navController.currentDestination?.route != menuItem.route) {
                navController.navigate(menuItem.route) { popUpTo(NavigationRoutes.Top) }
            }
        }
    )
}