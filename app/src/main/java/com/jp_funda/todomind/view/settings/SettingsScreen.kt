package com.jp_funda.todomind.view.settings

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.BuildConfig
import com.jp_funda.todomind.R
import com.jp_funda.todomind.navigation.NavigationRoute
import com.jp_funda.todomind.view.intro.IntroActivity
import com.jp_funda.todomind.view.settings.components.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalPagerApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
            )
        },
        backgroundColor = colorResource(id = R.color.deep_purple)
    ) {
        SettingsContent(navController)
    }
}

@ExperimentalPagerApi
@Composable
fun SettingsContent(navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<SettingsViewModel>()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        /** Personal Settings */
        SettingsGroup(title = "Personal Settings") {
            // Mind Map Scale
            SettingRowWithNext(
                icon = Icons.Default.LocationOn,
                title = "Default Mind Map Scale",
                selectedValue = "${(viewModel.defaultMindMapScale * 100).roundToInt()} %"
            ) { navController.navigate(NavigationRoute.MindMapScale) }

            Divider(color = colorResource(id = R.color.white_1))

            // OGP thumbnail setting
            SettingRowWithSwitch(
                painter = painterResource(id = R.drawable.ic_link_24),
                title = "Show Link Thumbnail",
                initialValue = viewModel.isShowOgpThumbnail,
            ) {
                viewModel.setIsShowOgpThumbnail(it)
            }
        }

        /** About TodoMind */

        /** About TodoMind */
        SettingsGroup("About TodoMind") {
            // APP version
            SettingRowOnlyText(
                icon = Icons.Default.Build,
                title = "App version",
                value = BuildConfig.VERSION_NAME
            )

            Divider(color = colorResource(id = R.color.white_1))

            // OSS Licenses
            SettingRowWithNext(
                icon = Icons.Default.List,
                title = "Open source licenses"
            ) { navController.navigate(NavigationRoute.OssLicenses) }

            Divider(color = colorResource(id = R.color.white_1))

            // Intro
            SettingRowOnlyText(
                icon = Icons.Default.Info,
                title = "Watch Introduction",
                value = "",
            ) {
                context.startActivity(Intent(context, IntroActivity::class.java))
            }
        }

        /** Notifications */

        /** Notifications */
        SettingsGroup("Notifications") {
            SettingRowWithSwitch(
                icon = Icons.Default.Notifications,
                title = "Remind task deadline",
                initialValue = viewModel.isRemindTaskDeadline,
            ) {
                viewModel.setIsRemindTaskDeadline(it)
            }
        }

        /** Under Construction */

        /** Under Construction */
        SettingsGroup("Under Development") {
            SettingRowComingSoon(
                painter = painterResource(id = R.drawable.ic_backup_24),
                title = "Cloud Backup",
                subTitle = "coming soon...",
            ) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "This feature is under construction",
                        actionLabel = "OK"
                    )
                }
            }
        }

        // Copy Light
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mind Map task management app",
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle2.fontSize * 0.8,
                modifier = Modifier.alpha(0.6f)
            )

            Divider(
                color = colorResource(id = R.color.white_1),
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.light_purple))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_mind_map),
                    contentDescription = "App icon",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(60.dp)
                        .alpha(0.6f),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "© 2022 by Masato Ishikawa",
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle2.fontSize * 0.8,
                modifier = Modifier.alpha(0.6f)
            )
        }
    }

    // Snackbar
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Status update Snackbar
        SnackbarHost(hostState = snackbarHostState)
    }
}
