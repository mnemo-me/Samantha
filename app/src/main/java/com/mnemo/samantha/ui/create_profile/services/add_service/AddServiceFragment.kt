package com.mnemo.samantha.ui.create_profile.services.add_service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentAddServiceBinding
import com.mnemo.samantha.repository.Repository


class AddServiceFragment : Fragment() {

    private lateinit var binding: FragmentAddServiceBinding
    private lateinit var viewModel: AddServiceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_service, container, false)
        val view = binding.root


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = AddServiceViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddServiceViewModel::class.java)


        // Close button click listener
        binding.addServiceCloseButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Done button click listener
        binding.addServiceDoneButton.setOnClickListener{
            view.findNavController().navigateUp()
        }


        return view
    }

}