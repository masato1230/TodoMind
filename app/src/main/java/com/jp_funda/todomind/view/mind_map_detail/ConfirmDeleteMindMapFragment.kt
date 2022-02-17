package com.jp_funda.todomind.view.mind_map_detail

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmDeleteMindMapFragment : DialogFragment() {

    companion object {
        val REQUEST_KEY = "CONFIRM_DELETE"
        val KEY = "IS_DELETE"
        val DELETE = true
        val CANCEL = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.question_confirm_delete)
                .setPositiveButton(R.string.delete,
                    DialogInterface.OnClickListener { dialog, id ->
                        setFragmentResult(REQUEST_KEY, bundleOf(KEY to DELETE))
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        setFragmentResult(REQUEST_KEY, bundleOf(KEY to CANCEL))
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}