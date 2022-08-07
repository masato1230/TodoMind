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
import com.jp_funda.todomind.R
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.data.SampleData
import com.jp_funda.repositories.task.entity.NodeStyle
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
class EditingTaskDetailScreenShould {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltActivity>()

    @Inject
    lateinit var createMindMapUseCase: CreateMindMapUseCase

    @Inject
    lateinit var createTasksUseCase: CreateTasksUseCase

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val task = SampleData.sampleTasks[0]
    private val mindMap = SampleData.mindMap

    @Before
    fun setUp() = runTest {
        hiltRule.inject()
        createMindMapUseCase(SampleData.mindMap)
        createTasksUseCase(SampleData.sampleTasks)

        composeRule.setContent {
            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()
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
        composeRule.waitForIdle()
        // Mind Map
        composeRule
            .onNodeWithContentDescription(appContext.getString(R.string.mind_map))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(mindMap.title.toString())
            .assertExists()
    }

    // Tests which assert initial state and input
    @Test
    fun haveTaskTitleTextField() {
        composeRule.waitForIdle()
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
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_DESCRIPTION, true).run {
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
        composeRule.waitForIdle()
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
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_TIME).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule.onNodeWithText("SELECT TIME").assertIsDisplayed()
    }

    @Test
    fun haveColorTextField() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_COLOR).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule.onNodeWithText("OK").assertIsDisplayed()
        composeRule.onNodeWithText("CANCEL").assertIsDisplayed()
    }

    @Test
    fun haveStyleTextField() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_STYLE).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule.onNodeWithText(NodeStyle.HEADLINE_1.title).assertIsDisplayed()
    }

    @Test
    fun haveParentTaskTextField() {
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTag.TASK_DETAIL_PARENT_TASK).run {
            // initial state assertion
            assertIsDisplayed()
            performClick()
        }
        // assert dialog is shown
        composeRule
            .onNodeWithText(appContext.getString(R.string.task_detail_parent_select_title), true)
            .assertIsDisplayed()
    }

    @Test
    fun showDeleteButton() {
        composeRule.waitForIdle()
        composeRule
            .onNodeWithText(appContext.getString(R.string.delete))
            .assertIsDisplayed()
    }
}
