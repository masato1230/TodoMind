package com.jp_funda.todomind.view.mind_map_create.nodes

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.task.entity.NodeStyle
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.todomind.extension.getSize
import com.jp_funda.repositories.jsoup.entity.OpenGraphResult
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun OgpCard(
    ogpResult: OpenGraphResult,
    task: Task,
    viewModel: MindMapCreateViewModel,
) {
    if (ogpResult.image.isNullOrBlank() || ogpResult.url.isNullOrBlank()) return

    val context = LocalContext.current
    val density = LocalDensity.current.density

    var offsetX by remember { mutableStateOf(task.x ?: 0f) }
    var offsetY by remember {
        mutableStateOf(
            (task.y ?: 0f) + NodeStyle.LINK.getSize().height * density + 30f
        )
    }
    // Update status before ui update
    offsetX = task.x ?: 0f
    offsetY = (task.y ?: 0f) + NodeStyle.LINK.getSize().height * density + 30f

    val scale = viewModel.getScale()

    Card(
        modifier = Modifier
            .offset { IntOffset((offsetX * scale).roundToInt(), (offsetY * scale).roundToInt()) }
            .width(150.dp * viewModel.getScale())
            .clickable {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(ogpResult.url))
                context.startActivity(browserIntent)
            }
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = ogpResult.image),
                contentDescription = "Site thumbnail",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.heightIn(max = 100.dp * viewModel.getScale())
            )
            ogpResult.siteName?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 5.dp * viewModel.getScale()),
                    fontSize = MaterialTheme.typography.caption.fontSize * viewModel.getScale()
                )
            }
            Text(
                text = ogpResult.url!!,
                color = Color.Blue,
                modifier = Modifier.padding(horizontal = 3.dp * viewModel.getScale()),
                fontSize = MaterialTheme.typography.caption.fontSize * viewModel.getScale(),
            )
            ogpResult.title?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    modifier = Modifier.padding(
                        top = 5.dp * viewModel.getScale(),
                        bottom = 10.dp * viewModel.getScale(),
                        start = 10.dp * viewModel.getScale(),
                        end = 10.dp * viewModel.getScale(),
                    ),
                    fontSize = MaterialTheme.typography.body1.fontSize * viewModel.getScale()
                )
            }
        }
    }
}