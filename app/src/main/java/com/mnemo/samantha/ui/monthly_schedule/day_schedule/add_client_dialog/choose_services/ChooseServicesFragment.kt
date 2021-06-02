package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.choose_services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentChooseServicesBinding


class ChooseServicesFragment : Fragment() {

    private lateinit var binding: FragmentChooseServicesBinding
    private lateinit var viewModel: ChooseServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_services, container, false)
        val view = binding.root


        // Get arguments
        val appointmentId = requireArguments().getLong("appointment_id")
        val clientId = requireArguments().getLong("client_id")


        // Create ViewModel via Factory
        val viewModelFactory = ChooseServicesViewModelFactory(appointmentId, clientId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChooseServicesViewModel::class.java)


        // Create adapter for RecyclerView
        val adapter = ChooseServicesAdapter()
        adapter.checkServiceClickListener = ChooseServicesAdapter.CheckServiceClickListener{position, service, isChecked ->
            viewModel.updateCheckedServices(service, isChecked)
            adapter.checkPosition(position, isChecked)
            updateTitleView()
        }

        binding.chooseServicesCheckList.adapter = adapter

        viewModel.services.observe(viewLifecycleOwner){services ->
           adapter.submitListWithCheck(services)
        }

        updateTitleView()

        // Close button click listener
        binding.chooseServicesCloseButton.setOnClickListener{
            view.findNavController().popBackStack(R.id.dayScheduleFragment, false)
        }

        // Done button click listener
        binding.chooseServicesDoneButton.setOnClickListener{
            viewModel.bookClient()
            view.findNavController().popBackStack(R.id.dayScheduleFragment, false)
        }

        return view
    }


    fun updateTitleView(){
        binding.chooseServicesTotalCost.text = getString(R.string.services_total_cost, viewModel.totalCost, "$")
        binding.chooseServicesTotalTime.text = getString(R.string.services_total_time, viewModel.totalTime)
    }

}