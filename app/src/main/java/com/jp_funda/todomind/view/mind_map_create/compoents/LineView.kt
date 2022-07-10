package com.jp_funda.todomind.view.mind_map_create.compoents

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.view.components.LineContent
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun LineView() {
    val context = LocalContext.current
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    LineContent(mindMapCreateViewModel = mindMapCreateViewModel, resources = context.resources)
}