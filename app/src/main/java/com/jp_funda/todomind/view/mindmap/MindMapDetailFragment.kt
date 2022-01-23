package com.jp_funda.todomind.view.mindmap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
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

    @Composable
    fun MindMapDetailContent() {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            // Thumbnail
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

            // Title
            // Description
            // Progress bar
            // Progress description
            // Created Date and Last Updated Date
            // Task list
        }
    }
}