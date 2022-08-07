package com.jp_funda.todomind.view.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jp_funda.repositories.jsoup.entity.OpenGraphResult

@Composable
fun OgpThumbnail(ogpResult: OpenGraphResult, context: Context) {
    if (ogpResult.image.isNullOrEmpty() || ogpResult.url.isNullOrEmpty()) return

    Card(
        backgroundColor = Color.Black
    ) {
        Column {
            Image(
                painter = rememberImagePainter(ogpResult.image),
                contentDescription = "Site thumbnail",
                modifier = Modifier
                    .heightIn(min = 0.dp, max = 200.dp)
                    .fillMaxWidth()
                    .clickable {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(ogpResult.url))
                        context.startActivity(browserIntent)
                    },
                contentScale = ContentScale.FillWidth,
            )
            ogpResult.title?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 10.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                )
            }
        }
    }
}