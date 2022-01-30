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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.jp_funda.todomind.R
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
        Button(onClick = { showDatePicker(requireActivity()) }) {
            Text("Hey")
        }
    }

    private fun showDatePicker(activity: FragmentActivity) {
        val date = Date().time
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(date)
            .build()
        activity.let {
            picker.show(it.supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                // TODO set date
            }
        }
    }
}