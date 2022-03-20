package com.jp_funda.todomind.view.settings.mind_map_scale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.BackNavigationIcon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MindMapScaleFragment : Fragment() {

    private val viewModel: MindMapScaleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Default Mind Map Scale") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                            navigationIcon = { BackNavigationIcon() },
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple)
                ) {
                    var scale by remember { mutableStateOf(viewModel.scale) }
                    Slider(value = scale, onValueChange = {
                        viewModel.scale = it
                        scale = it
                    })
                }
            }
        }
    }
}