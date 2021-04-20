package com.mnemo.samantha.ui.month_schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentMonthlyScheduleBinding

class MonthlyScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMonthlyScheduleBinding
    private lateinit var viewModel: MonthlyScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monthly_schedule, container, false)

        viewModel = ViewModelProvider(this).get(MonthlyScheduleViewModel::class.java)

        val adapter = MonthlyScheduleAdapter()
        viewModel.days.observe(viewLifecycleOwner, {days ->
            adapter.days = days
        })

        binding.monthScheduleSchedule.adapter = adapter

        return binding.root
    }

}