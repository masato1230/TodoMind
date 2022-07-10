package com.jp_funda.todomind.view.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

@Composable
fun ConfirmDialog(
    title: String,
    message: String?,
    isShowDialog: MutableState<Boolean>,
    isShowNegativeButton: Boolean = false,
    onClickPositive: () -> Unit = {},
    onClickNegative: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {
            isShowDialog.value = false
            onDismiss()
        },
        title = {
            Column {
                Text(text = title)
                message?.let {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = message, style = MaterialTheme.typography.body2)
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (isShowNegativeButton) {
                    Button(
                        modifier = Modifier.width(120.dp),
                        onClick = {
                            isShowDialog.value = false
                            onClickNegative()
                            onDismiss()
                        },
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                        isShowDialog.value = false
                        onClickPositive()
                        onDismiss()
                    },
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            }
        }
    )
}