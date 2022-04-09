package com.jp_funda.todomind.view.mind_map_create.tutorial

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.IntroPage

class MindMapCreateTutorialDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.Transparent.toArgb()))
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    color = colorResource(id = R.color.dark),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
//                        VideoPlayer()
                        IntroPage(
                            thumbnail = { /*TODO*/ },
                            mainText = "Move Node",
                            subText = "Drag & Drop to move a node(task or mind map)."
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun VideoPlayer() {
        // Fetching the Local Context
        val context = LocalContext.current

        // Declaring a string value
        // that stores raw video url
        val videoUrl =
            "https://cdn.videvo.net/videvo_files/video/free/2020-05/large_watermarked/3d_ocean_1590675653_preview.mp4"

        // Declaring ExoPlayer
        val exoPlayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {
                val dataSourceFactory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(videoUrl))
                val loopingMediaSource = LoopingMediaSource(source)
                prepare(loopingMediaSource)
            }
        }
        exoPlayer.playWhenReady = true

        // Implementing ExoPlayer
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    fitsSystemWindows = true
                }
            },
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        )
    }
}