package com.mnemo.samantha.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mnemo.samantha.R
import com.mnemo.samantha.data.MonthlyStatistics
import com.mnemo.samantha.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_statistics, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val statistics = mutableListOf<StatisticsAdapter.DataItem>()
        statistics.add(StatisticsAdapter.DataItem.Header("240 000.00 р"))

        for (i in 1..12){
            statistics.add(StatisticsAdapter.DataItem.MonthlyStatisticsItem(MonthlyStatistics(123, 2021, "March", 20, 48, 40000)))
        }

        val statisticsAdapter = StatisticsAdapter()
        statisticsAdapter.statistics = statistics

        binding.statistics.adapter = statisticsAdapter
    }

}