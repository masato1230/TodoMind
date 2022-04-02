package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

@Composable
fun IntroPage(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    mainText: String,
    subText: String,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            thumbnail()
        }

        Text(
            text = mainText,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = subText,
            color = Color.Gray,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}