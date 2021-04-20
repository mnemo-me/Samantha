package com.mnemo.samantha.ui.month_schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentMonthlyScheduleBinding

class MonthlyScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMonthlyScheduleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monthly_schedule, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val days = mutableListOf<String>()

        for (i in 1..31){
            days.add("$i")
        }

        val adapter = MonthlyScheduleAdapter()
        adapter.days = days

        binding.monthScheduleSchedule.adapter = adapter
    }

}