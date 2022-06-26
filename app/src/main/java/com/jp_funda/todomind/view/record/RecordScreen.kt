package com.jp_funda.todomind.view.record

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordScreen() {
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
        RecordContent()
    }
}

@Composable
fun RecordContent() {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.7f),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Coming soon...",
            color = Color.White,
            style = MaterialTheme.typography.h4,
        )


        Column {
            Image(
                painter = painterResource(id = R.drawable.img_under_constrution),
                contentDescription = "Under construction",
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "This feature is currently under development.",
                color = Color.LightGray,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}