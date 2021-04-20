package com.mnemo.samantha.ui.current_day

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mnemo.samantha.R
import com.mnemo.samantha.data.Client
import com.mnemo.samantha.databinding.FragmentCurrentDayBinding

class CurrentDayFragment : Fragment() {

    private lateinit var binding: FragmentCurrentDayBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_day, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val todaySchedule = linkedMapOf<Int, Client?>()

        todaySchedule[-1] = null

        for(i in 1..24){
            todaySchedule[13 + i] = Client(123,"Samantha $i", "")
        }

        val adapter: TodayClientsAdapter = TodayClientsAdapter()
        binding.todaySchedule.adapter = adapter

        adapter.todaySchedule = todaySchedule
    }

}