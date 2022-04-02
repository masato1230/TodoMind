package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun IntroPage(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    mainText: String,
    subText: String,
    copyrightText: String? = null,
) {
    Column(modifier = modifier.padding(horizontal = 40.dp)) {
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
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = subText,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        copyrightText?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = it, color = Color.Gray, style = MaterialTheme.typography.caption)
            }
        }
    }
}