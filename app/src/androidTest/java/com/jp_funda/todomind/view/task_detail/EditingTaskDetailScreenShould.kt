package com.jp_funda.todomind.view.task_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.Constant
import com.jp_funda.todomind.R
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.data.SampleData
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
class EditingTaskDetailScreenShould {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltActivity>()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val task = SampleData.sampleTasks[0]
    private val mindMap = SampleData.mindMap

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()
            mainViewModel.addSampleData()
            TaskDetailScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                taskId = task.id.toString(),
            )
        }
    }

    // Tests which assert isDisplayed
    @Test
    fun showHeaderTitle() {
        // Screen Title
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.back))
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_detail_editing_title))
            .assertIsDisplayed()
    }

    @Test
    fun showMindMapIcon() {
        // Mind Map
        Thread.sleep(Constant.NAV_ANIM_DURATION.toLong() * 2)
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.mind_map))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(mindMap.title.toString())
            .assertExists()
    }

    @Test
    fun showShowShimmerComponents() {
        composeRule
            .onAllNodesWithTag(TestTag.ANIMATED_SHIMMER)
            .onFirst()
            .assertIsDisplayed()
    }

    // Tests which assert initial state and input
    @Test
    fun haveTaskTitleTextField() {
        Thread.sleep(Constant.NAV_ANIM_DURATION.toLong() * 2)
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_TITLE).run {
            // initial state assertions
            assertIsDisplayed()
            assertTextEquals(task.title.toString())
            // assert editable
            performTextClearance()
            performTextInput("test")
            assertTextEquals("test")
        }
    }

    @Test
    fun haveTaskDescriptionTextField() {
        Thread.sleep(Constant.NAV_ANIM_DURATION.toLong() * 2)
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_DESCRIPTION).run {
            // initial state assertions
            assertIsDisplayed()
            assertTextEquals(task.description.toString())
            // assert editable
            performTextClearance()
            performTextInput("test")
            assertTextEquals("test")
        }
    }

    @Test
    fun haveDateTextField() {
        Thread.sleep(Constant.NAV_ANIM_DURATION.toLong() * 2)
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_DATE).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule.onNodeWithText("SELECT DATE").assertIsDisplayed()
    }

    @Test
    fun haveTimeTextField() {
        Thread.sleep(Constant.NAV_ANIM_DURATION.toLong() * 2)
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_TIME).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule.onNodeWithText("SELECT TIME").assertIsDisplayed()
    }

    @Test
    fun showDeleteButton() {
        Thread.sleep(Constant.NAV_ANIM_DURATION.toLong() * 2)
        composeRule
            .onNodeWithText(appContext.getString(R.string.delete))
            .assertIsDisplayed()
    }
}
