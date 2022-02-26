package com.jp_funda.todomind.view.mind_map_create.options_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jp_funda.todomind.view.components.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@ExperimentalMaterialApi
@AndroidEntryPoint
class MindMapOptionsSheet : BottomSheetDialogFragment() {

    // ViewModels
    private val addChildViewModel by viewModels<AddChildViewModel>()
    private val editTaskViewModel by viewModels<EditTaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val observedMode = editTaskViewModel.selectedMode.observeAsState()
                observedMode.value?.let { selectedMode ->
                    Column {
                        MindMapOptionsTabRow(selectedMode = selectedMode) {
                            editTaskViewModel.setMode(it)
                        }

                        // Add Child Option
                        if (selectedMode == MindMapOptionsMode.ADD_CHILD) {
                            TaskEditContent(
                                fragment = this@MindMapOptionsSheet,
                                taskEditableViewModel = addChildViewModel,
                                mainViewModel = null,
                            )
                        } else { // Edit Task Option
                            TaskEditContent(
                                fragment = this@MindMapOptionsSheet,
                                taskEditableViewModel = editTaskViewModel,
                                mainViewModel = null,
                            )
                        }
                    }
                }
            }
        }
    }
}