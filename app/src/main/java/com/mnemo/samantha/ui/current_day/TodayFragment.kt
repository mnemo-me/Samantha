package com.mnemo.samantha.ui.current_day

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)

        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)

        val adapter = TodayClientsAdapter()
        binding.todaySchedule.adapter = adapter

        viewModel.todaySchedule.observe(viewLifecycleOwner, {todaySchedule ->
            adapter.todaySchedule = todaySchedule
        })

        return binding.root
    }

}