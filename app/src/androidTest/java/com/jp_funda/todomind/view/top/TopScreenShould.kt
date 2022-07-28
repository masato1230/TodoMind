package com.jp_funda.todomind.view.top

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.di.AppModule
import com.jp_funda.todomind.view.HiltActivity
import com.jp_funda.todomind.view.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@UninstallModules(AppModule::class)
@HiltAndroidTest
class TopScreenShould {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltActivity>()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()
            TopScreen(navController, mainViewModel)
        }
    }

    @Test
    fun showAppIconAndAppName() {
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.desc_app_icon))
            .assertExists()
        composeRule
            .onNodeWithText(appContext.getString(R.string.app_name))
            .assertExists()
    }

    @Test
    fun showShimmerEffectForFirstLaunch() {
        
    }
}