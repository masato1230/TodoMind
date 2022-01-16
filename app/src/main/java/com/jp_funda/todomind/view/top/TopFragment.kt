package com.jp_funda.todomind.view.top

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import com.jp_funda.todomind.R

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
                Text(
                    text = "Jet pack",
                    color = Color.White
                )
            }
        }
    }
}