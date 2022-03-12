package com.jp_funda.todomind.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
                SettingsContent()
            }
        }
    }

    @Composable
    fun SettingsContent() {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SettingsGroup()
        }
    }

    @Composable
    fun SettingsGroup() {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(colorResource(id = R.color.steel_dark))
        ) {
            // TODO fill with data
            for (i in 0..4) {
                SettingRow(icon = Icons.Default.List, title = "Open source licenses")
                if (i != 4) {
                    Divider(color = colorResource(id = R.color.white_1))
                }
            }
        }
    }

    @Composable
    fun SettingRow(
        icon: ImageVector,
        title: String,
        selectedValue: String? = null,
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
            selectedValue?.let { Text(text = it) }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                tint = colorResource(id = R.color.grey),
                contentDescription = "Next"
            )
        }
    }
}

