package com.jp_funda.todomind.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap

@Composable
fun RecentMindMapSection(
    modifier: Modifier = Modifier,
    mindMap: MindMap?,
    onRecentMindMapClick: () -> Unit,
    onNewMindMapClick: () -> Unit,
) {
    Column(modifier = modifier.testTag(TestTag.RECENT_MIND_MAP_SECTION)) {
        // Recent Activity Section
        Text(
            text = "The Mind Map you are working on recently...",
            modifier = Modifier
                .padding(15.dp)
                .width(250.dp),
            color = Color.White,
            style = MaterialTheme.typography.h6,
        )
        Row(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            mindMap?.let {
                MindMapCard(
                    mindMap = mindMap,
                    onClick = onRecentMindMapClick,
                )
            } ?: run {
                AnimatedShimmer(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(200.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
            Button(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .height(200.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .testTag(TestTag.NEW_MIND_MAP_BUTTON),
                onClick = onNewMindMapClick,
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(id = R.string.desc_add),
                        modifier = Modifier
                            .size(50.dp)
                            .padding(bottom = 10.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.deep_purple))
                    )
                    Text(
                        text = "New Mind Map",
                        color = colorResource(id = R.color.deep_purple)
                    )
                }
            }
        }
    }
}