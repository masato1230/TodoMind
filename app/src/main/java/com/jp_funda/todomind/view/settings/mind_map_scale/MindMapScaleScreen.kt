package com.jp_funda.todomind.view.settings.mind_map_scale

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.BackNavigationIcon
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MindMapScaleScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Default Mind Map Scale") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
                navigationIcon = { BackNavigationIcon(navController) },
            )
        },
        backgroundColor = colorResource(id = R.color.deep_purple)
    ) {
        MindMapScaleContent()
    }
}

@Composable
fun MindMapScaleContent() {
    val viewModel = hiltViewModel<MindMapScaleViewModel>()
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${(scale * 100).roundToInt()} %",
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Slider(
                    value = scale,
                    valueRange = 0.1f..2f,
                    onValueChange = { value ->
                        scale = value
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