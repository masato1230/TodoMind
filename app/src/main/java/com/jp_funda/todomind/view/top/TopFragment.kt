package com.jp_funda.todomind.view.top

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.MindMapCard
import com.jp_funda.todomind.view.components.TaskRow

class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

    private lateinit var viewModel: TopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[TopViewModel::class.java]
//        return inflater.inflate(R.layout.top_fragment, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                TopContent()
            }
        }
    }

    @Composable
    fun TopContent() {

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            // Section Projects
            Text(
                text = "Mind Maps",
                modifier = Modifier.padding(
                    top = 40.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                color = Color.White,
                style = MaterialTheme.typography.h6
            )

            LazyRow(modifier = Modifier.padding(bottom = 30.dp)) {
                // todo fill with data
                items(items = List<String>(5) { "d" }) { str ->
                    MindMapCard({}) // todo create onClick
                }
            }

            // Section Todos
            Text(
                text = "Tasks",
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                style = MaterialTheme.typography.h6,
            )

            TaskList()
        }
    }

    // TopComponents
    @Composable
    fun TaskList() {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            List(5) { "d" }.forEach { str ->
                TaskRow()
            }
        }
    }
}