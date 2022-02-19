package com.jp_funda.todomind.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun WhiteButton(
    text: String,
    leadingIcon: ImageVector? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .width(150.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(1000.dp))
            .clickable { onClick() }
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (leadingIcon != null) {
            Icon(imageVector = leadingIcon, contentDescription = "white button icon")
            Spacer(modifier = Modifier.width(10.dp))
        }
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.button
        )
    }
}