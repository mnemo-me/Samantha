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
import com.mnemo.samantha.repository.Repository

class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServicesBinding
    private lateinit var viewModel: ServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)
        val view = binding.root


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = ServicesViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ServicesViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = ServicesAdapter()

        adapter.addNewServiceClickListener = ServicesAdapter.AddNewServiceClickListener {
            view.findNavController().navigate(R.id.action_servicesFragment_to_serviceEditFragment)
        }

        adapter.serviceClickListener = ServicesAdapter.ServiceClickListener { serviceId ->
            view.findNavController().navigate(R.id.action_servicesFragment_to_serviceEditFragment, bundleOf("service_id" to serviceId))
        }

        binding.servicesList.adapter = adapter

        viewModel.services.observe(viewLifecycleOwner, {services ->
            adapter.addHeaderAndSubmitList(services)
        })


        // Back button click listener
        binding.servicesBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Next button click listener
        binding.servicesNextButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_servicesFragment_to_createScheduleFragment)
        }


        return view
    }

}