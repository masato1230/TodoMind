package com.jp_funda.todomind.view.intro

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.launch

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
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                count = IntroPageInfo.values().size,
                state = pagerState,
                // Add 32.dp horizontal padding to 'center' the pages
                contentPadding = PaddingValues(horizontal = 32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IntroPageInfo.values()[page].compose()
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                horizontalArrangement = Arrangement.Center,
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = colorResource(id = R.color.teal_200),
                    inactiveColor = Color.DarkGray,
                    indicatorHeight = 5.dp,
                    indicatorWidth = 24.dp,
                    spacing = 10.dp,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .widthIn(500.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(1000.dp))
                    .background(
                        if (pagerState.currentPage + 1 < pagerState.pageCount) {
                            Color.White
                        } else {
                            colorResource(id = R.color.teal_200)
                        }
                    )
                    .clickable {
                        if (pagerState.currentPage + 1 < pagerState.pageCount) {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        } else {
                            onBackPressed()
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (pagerState.currentPage + 1 < pagerState.pageCount) {
                    Text(
                        text = "Next",
                        color = Color.Black,
                        style = MaterialTheme.typography.h6,
                    )
                } else {
                    Text(
                        text = "Start!",
                        color = Color.Black,
                        style = MaterialTheme.typography.h6,
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}