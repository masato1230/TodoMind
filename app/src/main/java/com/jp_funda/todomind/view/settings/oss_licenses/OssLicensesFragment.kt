package com.jp_funda.todomind.view.settings.oss_licenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.BackNavigationIcon

class OssLicensesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val webView = WebView(requireContext())
        webView.loadUrl("file:///android_asset/licenses.html")

        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "OSS licenses") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                            navigationIcon = { BackNavigationIcon() },
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple)
                ) {
                    AndroidView(factory = { webView }, modifier = Modifier.padding(it))
                }
            }
        }
    }
}