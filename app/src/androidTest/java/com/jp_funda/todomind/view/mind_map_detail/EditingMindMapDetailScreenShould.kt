package com.jp_funda.todomind.view.mind_map_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.SampleData
import com.jp_funda.todomind.R
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.di.AppModule
import com.jp_funda.todomind.use_case.mind_map.CreateMindMapUseCase
import com.jp_funda.todomind.use_case.task.CreateTasksUseCase
import com.jp_funda.todomind.view.HiltActivity
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
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
class EditingMindMapDetailScreenShould {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltActivity>()

    @Inject
    lateinit var createMindMapUseCase: CreateMindMapUseCase

    @Inject
    lateinit var createTasksUseCase: CreateTasksUseCase

    private lateinit var mindMapDetailViewModel: MindMapDetailViewModel
    private lateinit var mindMapThumbnailViewModel: MindMapCreateViewModel

    private val mindMap = SampleData.mindMap

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() = runTest {
        hiltRule.inject()
        createMindMapUseCase(SampleData.mindMap)
        createTasksUseCase(SampleData.sampleTasks)

        composeRule.setContent {
            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()
            mindMapDetailViewModel = hiltViewModel()
            mindMapThumbnailViewModel = hiltViewModel()

            MindMapDetailScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                mindMapId = mindMap.id.toString(),
            )
        }
    }

    // Tests which assert isDisplayed
    @Test
    fun showHeaderCorrectly() {
        // assert the title is displayed
        composeRule
            .onNodeWithText(appContext.getString(R.string.mind_map_detail_editing_title))
            .assertIsDisplayed()
        // assert the save button is displayed
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.save))
            .assertIsDisplayed()
        // assert the delete button is displayed
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.delete))
            .assertIsDisplayed()
    }

    @Test
    fun haveTitleTextField() {
        waitUntilMindMapLoaded()
        composeRule.onNodeWithTag(TestTag.MIND_MAP_DETAIL_TITLE).run {
            // initial state assertions
            assertIsDisplayed()
            assertTextEquals(mindMap.title.toString())
            // assert editable
            performTextClearance()
            performTextInput("test")
            assertTextEquals("test")
        }
    }

    @Test
    fun showMindMapThumbnailSection() {
        waitUntilTasksLoaded()
        composeRule
            .onNodeWithTag(TestTag.MIND_MAP_DETAIL_THUMBNAIL)
            .assertIsDisplayed()
    }

    @Test
    fun haveDescriptionTextField() {
        waitUntilMindMapLoaded()
        composeRule.onNodeWithTag(TestTag.MIND_MAP_DETAIL_DESCRIPTION).run {
            // initial state assertions
            assertIsDisplayed()
            assertTextEquals(mindMap.description.toString())
            // assert editable
            performTextClearance()
            performTextInput("test")
            assertTextEquals("test")
        }
    }

    @Test
    fun showSomeTasksWhenInProgressTabSelected() {
        waitUntilMindMapLoaded()
        // scroll to task list
        composeRule
            .onNodeWithTag(TestTag.TASK_LIST_COLUMN)
            .performScrollToNode(hasTestTag(TestTag.TASK_ROW))
        composeRule
            .onAllNodesWithTag(TestTag.TASK_ROW)
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun showSomeTasksWhenOpenTabSelected() {
        waitUntilMindMapLoaded()
        // scroll to task list
        composeRule
            .onNodeWithTag(TestTag.TASK_LIST_COLUMN)
            .performScrollToNode(hasTestTag(TestTag.TASK_ROW))
        // select open tab
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_open))
            .performClick()

        composeRule
            .onAllNodesWithTag(TestTag.TASK_ROW)
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun showSomeTasksWhenCompleteTabSelected() {
        waitUntilMindMapLoaded()
        // scroll to task list
        composeRule
            .onNodeWithTag(TestTag.TASK_LIST_COLUMN)
            .performScrollToNode(hasTestTag(TestTag.TASK_ROW))
        // select complete tab
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_complete))
            .performClick()

        composeRule
            .onAllNodesWithTag(TestTag.TASK_ROW)
            .onFirst()
            .assertIsDisplayed()
    }

    private fun waitUntilMindMapLoaded() {
        composeRule.waitUntil { mindMapDetailViewModel.mindMap.value != null }
    }

    private fun waitUntilTasksLoaded() {
        composeRule.waitUntil(2_000) { mindMapThumbnailViewModel.tasks.isNotEmpty() }
    }
}