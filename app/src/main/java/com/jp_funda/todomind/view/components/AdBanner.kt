package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jp_funda.todomind.BuildConfig

@Composable
fun BannerAd(
    width: Int,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            val adView = AdView(context)
            adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width))
            adView.adUnitId = BuildConfig.topBannerUnitId
            adView.loadAd(AdRequest.Builder().build())
            return@AndroidView adView
        },
    )
}