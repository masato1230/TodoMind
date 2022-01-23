package com.jp_funda.todomind.view.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R

@Composable
fun RecentMindMapSection(fragment: Fragment) {
    // Recent Activity Section
    Text(
        text = "The Mind Map you are working on recently...",
        modifier = Modifier
            .padding(15.dp)
            .width(250.dp),
        color = Color.White,
        style = MaterialTheme.typography.h6,
    )
    MindMapCard(
        modifier = Modifier.padding(bottom = 20.dp),
        onClick = {
            Log.d("Move", "move")
            findNavController(fragment).navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
        }
    )
}