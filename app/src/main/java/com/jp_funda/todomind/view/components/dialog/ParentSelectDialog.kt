package com.jp_funda.todomind.view.components.dialog

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun ParentSelectDialog(
    title: String = "Select Parent Node",
    mindMap: MindMap?,
    viewModel: TaskEditableViewModel,
    initialValue: Task?, // null means parent node is MindMap
    onSubmitButtonClick: (Task?) -> Unit,
    onDismissRequest: () -> Unit,
) {
    viewModel.loadTasksInSameMindMap()

    val isLoading by viewModel.isLoading.observeAsState(true)
    var selectedOption by remember { mutableStateOf(initialValue) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            color = colorResource(id = R.color.dark),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (!isLoading) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = title,
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(color = colorResource(id = R.color.white_1))

                    // mind map option
                    mindMap?.let {
                        com.jp_funda.todomind.view.components.RadioButton(
                            isSelected = selectedOption == null,
                            title = it.title ?: "",
                            color = it.color?.let { color -> Color(color) }
                                ?: run { colorResource(id = R.color.crimson) }) {
                            selectedOption = null
                        }
                    }

                    Divider(color = colorResource(id = R.color.white_1))

                    // task option
                    LazyColumn(modifier = Modifier.height(300.dp)) {
                        items(viewModel.tasksInSameMindMap) { task ->
                            com.jp_funda.todomind.view.components.RadioButton(
                                isSelected = task.id == selectedOption?.id,
                                title = task.title ?: "",
                                color = task.color?.let { Color(it) }
                                    ?: colorResource(id = R.color.teal_200),
                            ) {
                                selectedOption = task
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(color = colorResource(id = R.color.white_1))

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Cancel",
                            color = colorResource(id = R.color.teal_200),
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(10.dp).clickable {
                                onDismissRequest.invoke()
                            },
                        )
                        Text(
                            text = "OK",
                            color = colorResource(id = R.color.teal_200),
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(10.dp).clickable {
                                onSubmitButtonClick.invoke(selectedOption)
                            },
                        )
                    }
                }
            } else {
                LoadingView()
            }
        }
    }
}