package com.jp_funda.todomind.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.view.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        installSplashScreen()

        // Show intro
        if (!SettingsPreferences(this).getBoolean(PreferenceKeys.IS_SHOWED_INTRO)) {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        // Add sample data and Settings for first time launch
        SettingsPreferences(this).apply {
            if (!getBoolean(PreferenceKeys.IS_NOT_FIRST_TIME_LAUNCH)) {
                setBoolean(PreferenceKeys.IS_SHOW_OGP_THUMBNAIL, true)
                setBoolean(PreferenceKeys.IS_REMIND_TASK_DEADLINE, true)
                viewModel.addSampleData()
            }
        }

        // Google Ad mob
        MobileAds.initialize(this@MainActivity)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("64AFF500F888621E71FEFCF52C544C6B"))
                .build()
        )

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.deep_purple)
            ) {
                MainScreen()
            }
        }
    }
}