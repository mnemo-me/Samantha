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
import com.mnemo.samantha.repository.Repository

class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    private lateinit var viewModel: TodayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)


        // Create ViewModel via Factory
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = TodayViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TodayViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = TodayClientsAdapter()
        adapter.setDate(viewModel.date, viewModel.month, viewModel.dayOfWeek)
        adapter.buttonClickListener = TodayClientsAdapter.ButtonClickListener {  }

        binding.todaySchedule.adapter = adapter

        viewModel.todayClients.observe(viewLifecycleOwner, {todayClients ->
            adapter.addHeaderAndSubmitList(todayClients)
        })



        return binding.root
    }

}