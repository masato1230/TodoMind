package com.jp_funda.todomind.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.jp_funda.todomind.R

@Composable
fun RadioButton(
    isSelected: Boolean,
    title: String,
    color: Color,
    onClick: () -> Unit,
) {
    val clickableColors = TextFieldDefaults.textFieldColors(
        disabledTextColor = Color.LightGray,
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        cursorColor = colorResource(id = R.color.teal_200),
    )

    Row {
        TextField(
            colors = clickableColors,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick.invoke() },
            value = title,
            onValueChange = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        id =
                        if (!isSelected) R.drawable.ic_checkbox_unchecked
                        else R.drawable.ic_checkbox_checked
                    ),
                    tint = color,
                    contentDescription = "mind map status"
                )
            },
            readOnly = true,
            enabled = false,
        )
    }
}