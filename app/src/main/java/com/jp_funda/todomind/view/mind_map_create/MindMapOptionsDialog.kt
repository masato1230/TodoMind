package com.jp_funda.todomind.view.mind_map_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jp_funda.todomind.databinding.DialogMindMapOptionsBinding

class MindMapOptionsDialog : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "MindMapOptions"
    }

    private var _binding: DialogMindMapOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMindMapOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}