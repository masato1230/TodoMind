package com.jp_funda.todomind.view.components

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.luminance
import com.jp_funda.todomind.R
import com.jp_funda.repositories.mind_map.entity.MindMap

@Composable
fun MindMapCard(
    mindMap: MindMap,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.card_mind_map, null)
        },
        update = { view ->
            val tintColor =
                if (mindMap.color?.luminance ?: 0f > 0.6) Color.BLACK else Color.WHITE

            // Initialize view
            val background = view.findViewById<View>(R.id.map_card_background)
            val createdDate = view.findViewById<TextView>(R.id.map_card_created_date)
            val title = view.findViewById<TextView>(R.id.map_card_title)
            val description = view.findViewById<TextView>(R.id.map_card_description)

            // card background
            background.setBackgroundColor(mindMap.color ?: Color.parseColor("#e91e63"))

            // created date
            createdDate.text = MindMap.dateFormat.format(mindMap.createdDate)
            // title
            title.text = mindMap.title ?: ""
            title.setTextColor(tintColor)

            // description
            description.text =
                if (!mindMap.description.isNullOrBlank()) mindMap.description!! else "No description"
            description.setTextColor(tintColor)
        },
        modifier = modifier.clickable {
            onClick()
        }
    )
}