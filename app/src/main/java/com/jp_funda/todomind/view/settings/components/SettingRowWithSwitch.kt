package com.jp_funda.todomind.view.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

/** SettingRow with switch */
@Composable
fun SettingRowWithSwitch(
    icon: ImageVector? = null,
    painter: Painter? = null,
    title: String,
    initialValue: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val checkedState = remember { mutableStateOf(initialValue) }

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                tint = colorResource(id = R.color.grey),
                contentDescription = "Title",
                modifier = Modifier.height(40.dp)
            )
        }
        painter?.let {
            Icon(
                painter = it,
                tint = colorResource(id = R.color.grey),
                contentDescription = "Title",
                modifier = Modifier.height(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(Modifier.weight(1f))
        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(id = R.color.teal_200),
                checkedTrackColor = colorResource(id = R.color.teal_200),
                checkedTrackAlpha = 0.8f,
            )
        )
    }
}