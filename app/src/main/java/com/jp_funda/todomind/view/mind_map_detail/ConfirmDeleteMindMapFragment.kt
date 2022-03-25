package com.jp_funda.todomind.view.mind_map_detail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.jp_funda.todomind.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmDeleteMindMapFragment : DialogFragment() {

    companion object {
        const val REQUEST_KEY = "CONFIRM_DELETE"
        const val KEY = "IS_DELETE"
        const val DELETE = true
        const val CANCEL = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.question_confirm_delete)
                .setMessage(R.string.notify_tasks_delete)
                .setPositiveButton(R.string.delete
                ) { _, _ ->
                    setFragmentResult(REQUEST_KEY, bundleOf(KEY to DELETE))
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    setFragmentResult(REQUEST_KEY, bundleOf(KEY to CANCEL))
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}