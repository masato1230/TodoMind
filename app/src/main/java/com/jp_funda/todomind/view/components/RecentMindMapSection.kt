package com.jp_funda.todomind.view.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.mindmap.MindMapFragment
import com.jp_funda.todomind.view.top.TopFragment

@Preview
@Composable
fun RecentMindMapSection(fragment: Fragment = Fragment()) { // todo delete initial value
    Column {
        // Recent Activity Section
        Text(
            text = "The Mind Map you are working on recently...",
            modifier = Modifier
                .padding(15.dp)
                .width(250.dp),
            color = Color.White,
            style = MaterialTheme.typography.h6,
        )
        Row {
            MindMapCard(
                modifier = Modifier.padding(bottom = 20.dp, end = 30.dp),
                onClick = {
                    when (fragment) {
                        is TopFragment -> findNavController(fragment).navigate(R.id.action_navigation_top_to_navigation_mind_map_detail)
                        is MindMapFragment -> findNavController(fragment).navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                    }
                }
            )
            Button(
                onClick = {}, // todo navigate to add mind map view
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "add",
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