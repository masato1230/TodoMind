package com.jp_funda.todomind.view.settings.oss_licenses

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.BackNavigationIcon

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OssLicensesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "OSS licenses") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
                navigationIcon = { BackNavigationIcon(navController) },
            )
        },
        backgroundColor = Color.White,
    ) {
        OssLicensesContent()
    }
}

@Composable
fun OssLicensesContent() {
    val webView = WebView(LocalContext.current)
    LaunchedEffect(Unit) {
        webView.loadUrl("file:///android_asset/licenses.html")
    }

    AndroidView(factory = { webView })
}