package com.jp_funda.todomind.view.task_detail

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant.now
import java.util.*

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TaskDetailFragment()
    }

    private val taskDetailViewModel by viewModels<TaskDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TaskDetailContent()
            }
        }
    }

    @Composable
    fun TaskDetailContent() {
//        Button(onClick = { showDatePicker(requireActivity()) }) {
//            Text("Hey")
//        }
        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        DatePickerDialog(dateDialogState)
        TimePickerDialog(timeDialogState)
//        dateDialogState.show()
        timeDialogState.show()
    }

    @Composable
    private fun DatePickerDialog(
        dateDialogState: MaterialDialogState,
    ) {
        val colorTheme = DatePickerDefaults.colors(
            headerBackgroundColor = Color(resources.getColor(R.color.aqua)),
            headerTextColor = Color.White,
            activeBackgroundColor = Color.White,
            inactiveBackgroundColor = Color.Black,
            activeTextColor = Color.Black,
            inactiveTextColor = Color.White,
        )

        MaterialDialog(
            dialogState = dateDialogState,
            backgroundColor = Color(color = resources.getColor(R.color.aqua)),
            buttons = {
                positiveButton(
                    "OK",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = Color(resources.getColor(R.color.teal_200)),
                    )
                )
//                this.button("time", onClick = {
//                    dateDialogState.hide()
//                    timeDialogState.show()
//                })
                negativeButton(
                    "Cancel",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = Color(resources.getColor(R.color.teal_200)),
                    )
                )
            }
        ) {
            datepicker(
                colors = colorTheme,
                // TODO set initial value
            ) { date ->
                // TODO save date
            }
        }
    }

    @Composable
    fun TimePickerDialog(
        timeDialogState: MaterialDialogState,
    ) {
        val colorTheme = TimePickerDefaults.colors(
            activeBackgroundColor = Color.White,
            activeTextColor = Color.Black,
            inactiveBackgroundColor = Color(resources.getColor(R.color.navy_blue)),
            inactiveTextColor = Color.White,
            selectorColor = Color(resources.getColor(R.color.teal_200)),
            borderColor = Color(resources.getColor(R.color.aqua))
        )

        MaterialDialog(
            dialogState = timeDialogState,
            backgroundColor = Color(resources.getColor(R.color.aqua)),
            buttons = {
                positiveButton(
                    "OK",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = Color(resources.getColor(R.color.teal_200)),
                    )
                )
                negativeButton(
                    "Cancel",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = Color(resources.getColor(R.color.teal_200)),
                    )
                )
            },
        ) {
            timepicker(
                colors = colorTheme
            ) { time ->
                // Todo save time
            }
        }
    }
}