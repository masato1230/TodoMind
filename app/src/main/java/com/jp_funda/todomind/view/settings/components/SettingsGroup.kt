package com.jp_funda.todomind.view.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

@Composable
fun SettingsGroup(
    title: String,
    content: @Composable () -> Unit,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
        )
        Surface(
            elevation = 8.dp,
            shape = RoundedCornerShape(20.dp),
            color = colorResource(id = R.color.steel_dark)
        ) {
            Column { content() }
        }
    }
}