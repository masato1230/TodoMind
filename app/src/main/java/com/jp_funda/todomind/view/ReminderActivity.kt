package com.jp_funda.todomind.view

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
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.components.TaskEditContent
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class ReminderActivity : AppCompatActivity() {

    private val viewModel by viewModels<ReminderViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ComposeView(applicationContext)).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = if (viewModel.isEditing) "Task Detail" else "New Task") },
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
                    val isLoading by viewModel.isLoading.observeAsState(true)

                    if (!isLoading) {
                        TaskEditContent(
                            taskEditableViewModel = viewModel,
                            mainViewModel = mainViewModel,
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
    }

    // Navigate to MainActivity when back pressed
    override fun onBackPressed() {
        navigateToMainActivity()
    }
}