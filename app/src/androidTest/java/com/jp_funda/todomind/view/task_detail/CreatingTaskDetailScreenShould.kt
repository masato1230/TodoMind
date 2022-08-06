package com.jp_funda.todomind.view.task_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
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
class CreatingTaskDetailScreenShould {
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
            TaskDetailScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                taskId = null,
            )
        }
    }

    // Tests which assert isDisplayed
    @Test
    fun showHeaderCorrectly() {
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_detail_creating_title))
            .assertIsDisplayed()
    }

    @Test
    fun doNotShowShimmerComponents() {
        composeRule
            .onNodeWithTag(TestTag.ANIMATED_SHIMMER)
            .assertDoesNotExist()
    }

    @Test
    fun haveStatusTextField() {
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_STATUS).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule.onNodeWithText(TaskStatus.InProgress.name).assertIsDisplayed()
    }

    @Test
    fun showSaveButton() {
        composeRule
            .onNodeWithText(appContext.getString(R.string.save))
            .assertIsDisplayed()
    }

    @Test
    fun doNotContainDeleteButton() {
        composeRule
            .onNodeWithText(appContext.getString(R.string.delete))
            .assertDoesNotExist()
    }

    @Test
    fun showBannerAd() {
        composeRule.onNodeWithTag(TestTag.BANNER_AD).assertIsDisplayed()
    }
}