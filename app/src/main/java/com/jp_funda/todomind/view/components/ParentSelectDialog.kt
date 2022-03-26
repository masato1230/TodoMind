package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task

@Composable
fun ParentSelectDialog(
    title: String = "Select Parent Node",
    mindMap: MindMap?,
    optionsList: List<Task>,
    initialValue: Task?, // null means parent node is MindMap
    onSubmitButtonClick: (Task?) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var selectedOption by remember { mutableStateOf(initialValue) }

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier.height(300.dp),
            color = colorResource(id = R.color.dark),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = title)

                Spacer(modifier = Modifier.height(10.dp))

                // TODO add mindmap option
                LazyColumn {
                    items(optionsList) { task ->
                        RadioButton(selected = task == selectedOption, onClick = {
                            selectedOption = task
                        })
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = { onSubmitButtonClick.invoke(selectedOption) }) {
                    Text(text = "OK")
                }
            }
        }
    }
}