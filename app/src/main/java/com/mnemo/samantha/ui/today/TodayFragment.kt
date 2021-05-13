package com.mnemo.samantha.ui.today

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    private lateinit var viewModel: TodayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = TodayClientsAdapter()
        adapter.dateText = viewModel.dateText
        adapter.buttonClickListener = TodayClientsAdapter.ButtonClickListener {  }

        binding.todaySchedule.adapter = adapter

        viewModel.todayClients.observe(viewLifecycleOwner, {todayClients ->
            adapter.addHeaderAndSubmitList(todayClients)
        })



        return binding.root
    }

}