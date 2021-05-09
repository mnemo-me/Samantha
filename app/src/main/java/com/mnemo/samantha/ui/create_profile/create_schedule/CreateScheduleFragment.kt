package com.mnemo.samantha.ui.create_profile.create_schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentCreateScheduleBinding
import com.mnemo.samantha.repository.Repository

class CreateScheduleFragment : Fragment() {

    private lateinit var binding: FragmentCreateScheduleBinding
    private lateinit var viewModel: CreateScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_schedule, container, false)
        val view = binding.root


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = CreateScheduleViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateScheduleViewModel::class.java)


        // Back button click listener
        binding.createScheduleBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Done button click listener
        binding.createScheduleDoneButton.setOnClickListener{

        }

        return view
    }

}