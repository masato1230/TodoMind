package com.jp_funda.todomind.view.mindmap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController

class MindMapDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MindMapDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // return layout
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "kdkk")
            }
        }
    }
}