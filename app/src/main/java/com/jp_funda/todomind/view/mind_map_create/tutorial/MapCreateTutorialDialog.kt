package com.jp_funda.todomind.view.mind_map_create.tutorial

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.jp_funda.todomind.R


@Composable
fun MapCreateTutorialDialog(isShowDialog: MutableState<Boolean>) {
    if (isShowDialog.value) {
        Dialog(onDismissRequest = { isShowDialog.value = false }) {
            Surface(
                color = colorResource(id = R.color.light_purple),
                shape = RoundedCornerShape(10.dp),
            ) {
                var currentindex by remember { mutableStateOf(0) }
                val currentInfo = TutorialInfo.values()[currentindex]
                val pageCount = TutorialInfo.values().size

                Column {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 5.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.LightGray),
                    ) {
                        Text(
                            text = "How to use",
                            color = Color.Black,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(horizontal = 5.dp),
                        )
                    }

                    // Title
                    Text(
                        text = currentInfo.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 15.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.h5,
                    )

                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    // Video and Description
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(310.dp),
                    ) {
                        VideoPlayer(currentInfo.assetLink, isShowDialog)
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = currentInfo.description,
                                color = Color.LightGray,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .width(150.dp)
                            )
                        }
                    }

                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(25.dp))

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
                                } else isShowDialog.value = false
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

                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(assetLink: String, isShowDialog: MutableState<Boolean>) {

    // Implementing ExoPlayer
    AndroidView(
        factory = {
            StyledPlayerView(it).apply {
                player?.stop()
                player?.release()
                player = createPlayer(it, assetLink) {
                    isShowDialog.value = false
                }
                useController = false
                fitsSystemWindows = true
            }
        },
        update = {
            // Declaring ExoPlayer
            it.player?.stop()
            it.player?.release()
            it.player = createPlayer(it.context, assetLink) {
                isShowDialog.value = false
            }
        },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .width(150.dp)
            .heightIn(max = 310.dp),
    )
}

private fun createPlayer(context: Context, assetLink: String, onError: (e: Exception) -> Unit): ExoPlayer {
    val videoUri = Uri.parse(assetLink)
    val mediaItem = MediaItem.fromUri(videoUri)

    // Declaring ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(mediaItem)
        playWhenReady = true
        repeatMode = Player.REPEAT_MODE_ALL
        addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                onError(error)
            }
        })

        prepare()
    }
    return exoPlayer
}
