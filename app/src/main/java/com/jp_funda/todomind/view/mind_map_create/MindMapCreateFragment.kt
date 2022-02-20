package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}