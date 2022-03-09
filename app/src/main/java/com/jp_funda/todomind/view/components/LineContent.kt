package com.jp_funda.todomind.view.components

import android.content.res.Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@Composable
fun LineContent(
    mindMapCreateViewModel: MindMapCreateViewModel,
    resources: Resources,
) {
    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

    // Update when updateCount is countUp
    observedUpdateCount.value?.let { _ ->
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                for (task in mindMapCreateViewModel.tasks) {
                    val startSizeOffsetX = (task.parentTask?.styleEnum
                        ?: NodeStyle.HEADLINE_1).getSize().width / 2 * resources.displayMetrics.density
                    val startSizeOffsetY = (task.parentTask?.styleEnum
                        ?: NodeStyle.HEADLINE_1).getSize().height / 2 * resources.displayMetrics.density
                    val endSizeOffsetX =
                        task.styleEnum.getSize().width / 2 * resources.displayMetrics.density
                    val endSizeOffsetY =
                        task.styleEnum.getSize().height / 2 * resources.displayMetrics.density

                    val startOffsetX = task.parentTask?.x ?: mindMapCreateViewModel.mindMap.x
                    val startOffsetY = task.parentTask?.y ?: mindMapCreateViewModel.mindMap.y
                    val endOffsetX = task.x
                    val endOffsetY = task.y

                    if (
                        startOffsetX == null ||
                        startOffsetY == null ||
                        endOffsetX == null ||
                        endOffsetY == null
                    ) return@drawBehind

                    drawLine(
                        color = if (task.statusEnum != TaskStatus.Complete) Color.White else Color.DarkGray,
                        start = Offset(
                            startOffsetX + startSizeOffsetX,
                            startOffsetY + startSizeOffsetY
                        ) * mindMapCreateViewModel.getScale(),
                        end = Offset(
                            endOffsetX + endSizeOffsetX,
                            endOffsetY + endSizeOffsetY
                        ) * mindMapCreateViewModel.getScale(),
                        strokeWidth = Stroke.DefaultMiter
                    )
                }
            })
    }
}