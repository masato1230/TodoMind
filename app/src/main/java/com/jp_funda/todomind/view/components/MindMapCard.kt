package com.jp_funda.todomind.view.components

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap

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
            // Initialize view
            val createdDate = view.findViewById<TextView>(R.id.map_card_created_date)
            val title = view.findViewById<TextView>(R.id.map_card_title)
            val progressPercentageText = view.findViewById<TextView>(R.id.map_card_progress_percentage)
            val progressBar = view.findViewById<ProgressBar>(R.id.map_card_progress_bar)

            // created date
            mindMap.createdDate?.let {
                createdDate.text = MindMap.dateFormat.format(it)
            } ?: run {
                createdDate.visibility = View.GONE
            }
            // title
            title.text = mindMap.title ?: ""
            // TODO progressPercentageText
            // TODO progressBar
        },
        modifier = modifier.clickable {
            onClick()
        }
    )
}