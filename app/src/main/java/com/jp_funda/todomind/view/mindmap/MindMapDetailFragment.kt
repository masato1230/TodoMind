package com.jp_funda.todomind.view.mindmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

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
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Description Section
            // Title
            Text(
                text = "Mind Map Title",
                modifier = Modifier.padding(bottom = 10.dp),
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
            // Description
            Text(
                text = "This is description of the mind map. this is description of mind map. this is description of mind map",
                modifier = Modifier.padding(bottom = 10.dp),
                style = MaterialTheme.typography.body2,
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.height(10.dp))

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
            // Created Date and Last Updated Date

            // Task list Section
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