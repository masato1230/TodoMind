package com.jp_funda.todomind.view.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.R

class RecordFragment : Fragment() {

    private lateinit var viewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Record") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple)
                ) {
                    // TODO something
                }
            }
        }
    }
}