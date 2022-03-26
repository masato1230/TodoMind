package com.jp_funda.todomind.view.task_reminder

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.shared_preferences.NotificationPreferences
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.view.MainActivity
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.components.TaskEditContent
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class TaskReminderActivity : AppCompatActivity() {

    private val viewModel by viewModels<TaskReminderViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var notificationPreferences: NotificationPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get reminding task id from shared preference
        val taskId = notificationPreferences.getString(PreferenceKeys.REMINDING_TASK_ID)
        taskId?.let { viewModel.getTask(UUID.fromString(it)) }

        setContentView(ComposeView(applicationContext)).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Task Detail") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                            navigationIcon = {
                                IconButton(onClick = { navigateToMainActivity() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple),
                ) {
                    val loading by viewModel.loading.observeAsState(true)

                    if (!loading) {
                        TaskEditContent(
                            taskEditableViewModel = viewModel,
                            mainViewModel = mainViewModel,
                            isReminder = true,
                        ) { navigateToMainActivity() }
                    } else {
                        LoadingView()
                    }
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    // Navigate to MainActivity when back pressed
    override fun onBackPressed() {
        navigateToMainActivity()
    }
}