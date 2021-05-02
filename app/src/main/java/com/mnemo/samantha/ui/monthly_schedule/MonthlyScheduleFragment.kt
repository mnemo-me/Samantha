package com.mnemo.samantha.ui.monthly_schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentMonthlyScheduleBinding

class MonthlyScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMonthlyScheduleBinding
    private lateinit var viewModel: MonthlyScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monthly_schedule, container, false)
        val view = binding.root


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(MonthlyScheduleViewModel::class.java)


        // Create adapter for GridView
        val adapter = MonthlyScheduleAdapter()

        binding.monthScheduleSchedule.adapter = adapter
        binding.monthScheduleSchedule.onItemClickListener = AdapterView.OnItemClickListener { parent, itemView, position, id ->
            view.findNavController().navigate(R.id.action_navigation_month_to_dayScheduleFragment) }


        viewModel.days.observe(viewLifecycleOwner, {days ->
            adapter.days = days
        })


        return view
    }

}