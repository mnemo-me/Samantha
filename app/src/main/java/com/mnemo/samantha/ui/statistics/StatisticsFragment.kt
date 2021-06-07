package com.mnemo.samantha.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_statistics, container, false)


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = StatisticsAdapter()
        viewModel.annualRevenue.observe(viewLifecycleOwner){annualRevenue ->
            adapter.annualRevenue = annualRevenue
        }
        binding.statistics.adapter = adapter

        viewModel.statistics.observe(viewLifecycleOwner, {statistics ->
            adapter.addHeaderAndSubmitList(statistics)
        })

        return binding.root
    }
}