package com.jp_funda.todomind.view.top

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.data.SampleData
import com.jp_funda.todomind.di.AppModule
import com.jp_funda.todomind.use_case.mind_map.CreateMindMapUseCase
import com.jp_funda.todomind.use_case.task.CreateTasksUseCase
import com.jp_funda.todomind.view.HiltActivity
import com.jp_funda.todomind.view.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
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

    @Inject
    lateinit var createMindMapUseCase: CreateMindMapUseCase

    @Inject
    lateinit var createTasksUseCase: CreateTasksUseCase

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() = runTest {
        hiltRule.inject()

        createMindMapUseCase(SampleData.mindMap)
        createTasksUseCase(SampleData.sampleTasks)

        composeRule.setContent {
            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()
            TopScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }

    // Tests which asserts isDisplayed
    @Test
    fun showAppIconAndAppName() {
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.desc_app_icon))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(appContext.getString(R.string.app_name))
            .assertIsDisplayed()
    }

    @Test
    fun showNewTaskFAB() {
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_new_task))
            .assertIsDisplayed()
    }

    @Test
    fun showShimmerWhichHasRecentMindMapSectionAsAnAncestor() {
        composeRule
            .onAllNodesWithTag(TestTag.ANIMATED_SHIMMER)
            .onFirst()
            .onAncestors()
            .filterToOne(hasTestTag(TestTag.RECENT_MIND_MAP_SECTION))
            .assertIsDisplayed()
    }

    @Test
    fun showNewMindMapButton() {
        composeRule.onNodeWithTag(TestTag.NEW_MIND_MAP_BUTTON).assertIsDisplayed()
    }

    @Test
    fun showBannerAd() {
        composeRule.onNodeWithTag(TestTag.BANNER_AD).assertIsDisplayed()
    }

    @Test
    fun showTaskTab() {
        // Is displayed "In Progress" tab
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_in_progress))
            .assertIsDisplayed()
        // Is displayed "Open" tab
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_open))
            .assertIsDisplayed()
        // Is displayed "Complete" tab
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_complete))
            .assertIsDisplayed()
    }

    @Test
    fun showShimmerWhichHasTaskListAsAnAncestor() {
        composeRule
            .onAllNodesWithTag(TestTag.ANIMATED_SHIMMER)
            .onLast()
            .onAncestors()
            .filterToOne(hasTestTag(TestTag.TASK_LIST_COLUMN))
            .assertIsDisplayed()
    }

    @Test
    fun showTaskRow() {
        composeRule
            .onAllNodesWithTag(TestTag.TASK_ROW)
            .onFirst()
            .assertIsDisplayed()
    }
}