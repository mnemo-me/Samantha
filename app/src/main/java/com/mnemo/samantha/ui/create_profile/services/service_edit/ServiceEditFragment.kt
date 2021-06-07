package com.mnemo.samantha.ui.create_profile.services.service_edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentServiceEditBinding
import com.mnemo.samantha.domain.entities.Service


class ServiceEditFragment : Fragment() {

    private lateinit var binding: FragmentServiceEditBinding
    private lateinit var viewModel: ServiceEditViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_edit, container, false)
        val view = binding.root


        // Get arguments
        val serviceId = requireArguments().getLong("service_id")


        // Set title
        if (serviceId != 0L) {
            binding.serviceEditTitle.setText(R.string.edit_service)
        }else{
            binding.serviceEditTitle.setText(R.string.add_service)
        }


        // Create ViewModel via Factory
        val viewModelFactory = ServiceEditViewModelFactory(serviceId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ServiceEditViewModel::class.java)


        // Bind service
        viewModel.service.observe(viewLifecycleOwner, { service ->
            if (serviceId != 0L){
                binding.serviceEditName.setText(service.name)
                binding.serviceEditPrice.setText(service.price.toString())
                binding.serviceEditTime.setText(service.timeToComplete.toString())
            }
        })


        // Close button click listener
        binding.serviceEditCloseButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Done button click listener
        binding.serviceEditDoneButton.setOnClickListener{
            val name = binding.serviceEditName.text.toString()
            val price = binding.serviceEditPrice.text.toString().toLong()
            val time = binding.serviceEditTime.text.toString().toInt()

            viewModel.updateService(Service(serviceId, name, price, time))

            view.findNavController().navigateUp()

            if (serviceId != 0L){
                Snackbar.make(view, getText(R.string.service_updated), Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(view, getText(R.string.service_added), Snackbar.LENGTH_SHORT).show()
            }
        }


        return view
    }

}