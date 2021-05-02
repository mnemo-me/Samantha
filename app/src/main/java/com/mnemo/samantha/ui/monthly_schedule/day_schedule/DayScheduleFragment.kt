package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentDayScheduleBinding
import com.mnemo.samantha.repository.database.SamanthaDatabase

class DayScheduleFragment : Fragment() {

    private lateinit var binding: FragmentDayScheduleBinding
    private lateinit var viewModel: DayScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_day_schedule, container, false)


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val dataSource = SamanthaDatabase.getInstance(application).appointmentDAO

        val viewModelFactory = DayScheduleViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DayScheduleViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        // Create adapter for RecycleView
        val adapter = DayScheduleAdapter()
        binding.dayScheduleSchedule.adapter = adapter

        viewModel.appointments.observe(viewLifecycleOwner, { appointments ->
            adapter.addHeaderAndSubmitList(appointments)
        })


        return binding.root
    }

}