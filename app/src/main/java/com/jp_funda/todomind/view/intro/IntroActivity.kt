package com.jp_funda.todomind.view.intro

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jp_funda.todomind.R
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ComposeView(this)).apply {
            setContent {
                Scaffold(
                    backgroundColor = colorResource(id = R.color.deep_purple),
                ) {
                    IntroContents()
                }
            }
        }
    }

    @Composable
    private fun IntroContents() {
        val pagerState = rememberPagerState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Display 10 items
            HorizontalPager(
                count = 5,
                state = pagerState,
                // Add 32.dp horizontal padding to 'center' the pages
                contentPadding = PaddingValues(horizontal = 32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) { page ->
                Text("android", color = Color.White)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Color.LightGray,
                    inactiveColor = Color.DarkGray,
                    indicatorWidth = 16.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(0.1f),
                )
            }
        }
    }
}