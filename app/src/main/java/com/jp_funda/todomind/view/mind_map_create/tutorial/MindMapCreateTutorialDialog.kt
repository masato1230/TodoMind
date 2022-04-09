package com.jp_funda.todomind.view.mind_map_create.tutorial

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.DialogFragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import com.jp_funda.todomind.R

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
                    var currentindex by remember { mutableStateOf(0) }
                    val currentInfo = TutorialInfo.values()[currentindex]
                    val pageCount = TutorialInfo.values().size

                    Column {
                        // Title
                        Text(
                            text = currentInfo.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = MaterialTheme.typography.h5,
                        )

                        // Video and Description
                        Row(
                            modifier = Modifier.padding(10.dp),
                        ) {
                            VideoPlayer(currentInfo.rawResId)
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = currentInfo.description,
                                    color = Color.LightGray,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .width(150.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Next or Start button
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .widthIn(500.dp)
                                .height(50.dp)
                                .clip(RoundedCornerShape(1000.dp))
                                .background(
                                    if (currentindex + 1 < pageCount) {
                                        Color.White
                                    } else {
                                        colorResource(id = R.color.teal_200)
                                    }
                                )
                                .clickable {
                                    if (currentindex + 1 < pageCount) {
                                        currentindex++
                                    } else dismiss()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (currentindex + 1 < pageCount) {
                                Text(
                                    text = "Next",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.h6,
                                )
                            } else {
                                Text(
                                    text = "Start",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.h6,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun VideoPlayer(rawResId: Int) {
        // Declaring ExoPlayer
        val exoPlayer = createPlayer(LocalContext.current, rawResId)

        // Implementing ExoPlayer
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player?.release()
                    player = exoPlayer
                    useController = false
                    fitsSystemWindows = true
                }
            },
            update = {
                // Declaring ExoPlayer
                it.player?.release()
                val exoplayer = createPlayer(it.context, rawResId)
                it.player = exoplayer
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(150.dp)
                .heightIn(max = 320.dp),
        )
    }

    private fun createPlayer(context: Context, rawResId: Int): ExoPlayer {
        val videoUri = RawResourceDataSource.buildRawResourceUri(rawResId)
        // Declaring ExoPlayer
        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri)
            val loopingMediaSource = LoopingMediaSource(source)
            prepare(loopingMediaSource)
        }
        exoPlayer.playWhenReady = true
        return exoPlayer
    }
}