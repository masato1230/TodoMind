package com.jp_funda.todomind.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.BuildConfig
import com.jp_funda.todomind.R
import dagger.hilt.android.AndroidEntryPoint

// TODO add dismiss ogp tag setting
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
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
                    SettingsContent()
                }
            }
        }
    }

    @Composable
    fun SettingsContent() {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SettingsGroup("About TodoMind") {
                // TODO fill with data
                // APP version
                SettingRow(
                    icon = Icons.Default.Build,
                    title = "App version",
                    value = BuildConfig.VERSION_NAME
                )

                // OSS Licenses
                SettingRow(
                    icon = Icons.Default.List,
                    title = "Open source licenses"
                ) { findNavController().navigate(R.id.action_navigation_settings_to_navigation_oss_licenses) }
                Divider(color = colorResource(id = R.color.white_1))
            }
        }
    }

    @Composable
    fun SettingsGroup(
        title: String,
        content: @Composable () -> Unit,
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
            )
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(colorResource(id = R.color.steel_dark))
            ) { content() }
        }
    }

    /** SettingRow with only text info */
    @Composable
    fun SettingRow(
        icon: ImageVector,
        title: String,
        value: String,
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                tint = colorResource(id = R.color.grey),
                contentDescription = "Title",
                modifier = Modifier.height(40.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(Modifier.weight(1f))
            Text(text = value, color = Color.White)
        }
    }

    /** SettingRow with next screen */
    @Composable
    fun SettingRow(
        icon: ImageVector,
        title: String,
        selectedValue: String? = null,
        onClick: () -> Unit,
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 15.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                tint = colorResource(id = R.color.grey),
                contentDescription = "Title",
                modifier = Modifier.height(40.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(Modifier.weight(1f))
            selectedValue?.let { Text(text = it, color = Color.White) }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                tint = colorResource(id = R.color.grey),
                contentDescription = "Next"
            )
        }
    }
}

