package com.jp_funda.todomind.view.mindmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.TaskLists
import com.jp_funda.todomind.view.components.TaskRow

// TODO add delete button to action bar
class MindMapDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MindMapDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // return layout
        return ComposeView(requireContext()).apply {
            setContent {
                MindMapDetailContent()
            }
        }
    }

    @Preview
    @Composable
    fun MindMapDetailContent() {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            item {
                // Title
                Text(
                    text = "Mind Map Title",
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                ) // TODO add click listener to edit view
                // Thumbnail Section
                Image(
                    painter = painterResource(
                        id = R.drawable.img_mind_map_sample // TODO change image to real mind map
                    ),
                    contentDescription = "Mind Map description",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                ) // TODO add click listener to go to mind map edit view

                Spacer(modifier = Modifier.height(20.dp))

                // Date and Edit Mind Map Button Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Date
                    Text(
                        text = "Created on: Fri 10/20",
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White
                    )
                    // Edit Mind Map Button
                    Button(
                        onClick = {},
                        modifier = Modifier.clip(RoundedCornerShape(1000.dp)),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) { // TODO set OnClick
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Mind Map")
                            Image(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Description
                Text(
                    text = "This is description of the mind map. this is description of mind map. this is description of mind map",
                    modifier = Modifier.padding(bottom = 10.dp),
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray,
                ) // TODO add click listener to edit descriptions view

                Spacer(modifier = Modifier.height(10.dp))

                // Progress Section
                // Progress description
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "Progress: ",
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "70%",
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }
                // Progress bar
                RoundedProgressBar(percent = 70)

                Spacer(modifier = Modifier.height(30.dp))

                // Task list Section
                var selectedTabIndex by remember { mutableStateOf(0) }
                Text(
                    text = "Tasks - Mind Map Title",
                    color = Color.White,
                    style = MaterialTheme.typography.h6
                )
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    backgroundColor = colorResource(id = R.color.deep_purple),
                    contentColor = Color.White,
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = {
                            // todo add filtering
                            selectedTabIndex = 0
                        },
                        text = { Text("In Progress") }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = {
                            // todo add filtering
                            selectedTabIndex = 1
                        },
                        text = { Text("Open") }
                    )
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = {
                            // todo add filtering
                            selectedTabIndex = 2
                        },
                        text = { Text("Closed") }
                    )
                }
            }
            items(items = List(10) { "d" }) { str ->
                TaskRow()
            }
        }
    }

    // Mind Map Detail Components
    @Composable
    fun RoundedProgressBar(
        percent: Int,
        height: Dp = 10.dp,
        modifier: Modifier = Modifier,
        backgroundColor: Color = colorResource(id = R.color.white_10),
        foregroundColor: Brush = Brush.horizontalGradient(
            listOf(colorResource(id = R.color.teal_200), colorResource(id = R.color.teal_200))
        ),
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Box(
                modifier = modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .height(height)
            )
            Box(
                modifier = modifier
                    .background(foregroundColor)
                    .width(maxWidth * percent / 100)
                    .height(height)
            )
        }
    }
}