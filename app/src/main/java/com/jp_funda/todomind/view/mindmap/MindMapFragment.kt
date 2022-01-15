package com.jp_funda.todomind.view.mindmap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jp_funda.todomind.R

class MindMapFragment : Fragment() {

    companion object {
        fun newInstance() = MindMapFragment()
    }

    private lateinit var viewModel: MindMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mind_map_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MindMapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}