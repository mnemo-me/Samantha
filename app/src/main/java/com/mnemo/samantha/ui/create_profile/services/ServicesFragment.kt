package com.mnemo.samantha.ui.create_profile.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentServicesBinding


class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServicesBinding
    private lateinit var viewModel: ServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)
        val view = binding.root


        // Get master id
        val masterId = requireArguments().getLong("master_id")
        if (masterId != 0L) {
            binding.servicesBackButton.setImageResource(R.drawable.outline_close_black_24)
            binding.servicesNextButton.setImageResource(R.drawable.outline_done_black_24)
        }


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = ServicesAdapter()
        adapter.attachHeader()

        adapter.addNewServiceClickListener = ServicesAdapter.AddNewServiceClickListener {
            if (masterId != 0L){
                view.findNavController().navigate(R.id.action_servicesFragment_to_serviceEditFragment)
            }else {
                view.findNavController().navigate(R.id.action_servicesFragmentCreateProfile_to_serviceEditFragmentCreateProfile)
            }
        }

        adapter.serviceClickListener = ServicesAdapter.ServiceClickListener { serviceId ->
            if (masterId != 0L){
                view.findNavController().navigate(R.id.action_servicesFragment_to_serviceEditFragment, bundleOf("service_id" to serviceId))
            }else {
                view.findNavController().navigate(R.id.action_servicesFragmentCreateProfile_to_serviceEditFragmentCreateProfile, bundleOf("service_id" to serviceId))
            }
        }

        binding.servicesList.adapter = adapter

        viewModel.services.observe(viewLifecycleOwner, {services ->
            adapter.submitServicesList(services)
        })


        // Back button click listener
        binding.servicesBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Next button click listener
        binding.servicesNextButton.setOnClickListener{
            if (masterId != 0L){
                view.findNavController().navigateUp()
            }else {
                view.findNavController().navigate(R.id.action_servicesFragmentCreateProfile_to_createScheduleFragmentCreateProfile)
            }
        }


        return view
    }

}