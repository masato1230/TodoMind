package com.jp_funda.todomind.view.settings.mind_map_scale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.BackNavigationIcon
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

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

                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = "Scale (min: 10%, max: 200%)",
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.LightGray,
                            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
                        )
                        Surface(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(20.dp),
                            color = colorResource(id = R.color.steel_dark)
                        ) {
                            Column(horizontalAlignment = CenterHorizontally) {
                                Text(
                                    text = "${(scale * 100).roundToInt()} %",
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                                Slider(
                                    value = scale,
                                    valueRange = 0.1f..2f,
                                    onValueChange = {
                                        scale = it
                                    },
                                    onValueChangeFinished = {
                                        viewModel.scale = scale
                                    },
                                    colors = SliderDefaults.colors(
                                        thumbColor = colorResource(id = R.color.teal_200),
                                        activeTrackColor = colorResource(id = R.color.teal_200),
                                        inactiveTrackColor = Color.DarkGray,
                                    ),
                                    steps = 18,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}