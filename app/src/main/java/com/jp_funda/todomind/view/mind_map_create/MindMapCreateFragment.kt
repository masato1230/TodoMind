package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.R
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MindMapCreateFragment : Fragment() {

    private var _binding: FragmentMindMapCreateBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        binding.root.composeView.apply {
            setContent { 
                Scaffold(backgroundColor = colorResource(id = R.color.deep_purple)) {
                    MindMapCreateContent()
                }
            }
        }
        return binding.root
    }

    @Composable
    fun MindMapCreateContent() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}