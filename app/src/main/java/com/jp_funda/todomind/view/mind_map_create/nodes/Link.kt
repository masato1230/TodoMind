package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.NodeStyle
import com.jp_funda.todomind.domain.use_cases.ogp.entity.OpenGraphResult
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.getSize
import com.jp_funda.todomind.data.repositories.task.entity.getTextSize
import com.jp_funda.todomind.util.UrlUtil
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Link(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    val ogpResult = remember { mutableStateOf<OpenGraphResult?>(null) }
    val cachedSiteUrl = remember { mutableStateOf("") }
    val siteUrl = UrlUtil.extractURLs(text = task.description.toString()).firstOrNull()
    // Fetch ogp only first time
    if (cachedSiteUrl.value.isEmpty() && !siteUrl.isNullOrBlank()) {
        viewModel.fetchOgp(
            siteUrl = siteUrl,
            onSuccess = { result ->
                ogpResult.value = result
                cachedSiteUrl.value = result.url ?: ""
            },
            onError = { cachedSiteUrl.value = "" }
        )
    }

    // Reset Ogp when task.description is changed
    val rememberedDescription = remember { mutableStateOf(task.description) }
    if (task.description != rememberedDescription.value) {
        ogpResult.value = null
        cachedSiteUrl.value = ""
        rememberedDescription.value = task.description
    }

    Column {
        BodyNodeBase(
            modifier = modifier,
            task = task,
            viewModel = viewModel,
            iconImage = painterResource(id = R.drawable.ic_link_24),
            size = NodeStyle.BODY_1.getSize(),
            fontSize = NodeStyle.BODY_1.getTextSize(),
            maxLines = 1,
        ) { onClick() }
    }
    ogpResult.value?.let {
        OgpCard(ogpResult = it, task = task, viewModel = viewModel)
    }
}