package com.mnemo.samantha.ui.profile

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
import com.mnemo.samantha.databinding.FragmentProfileBinding
import com.mnemo.samantha.ui.create_profile.services.ServicesAdapter


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val view = binding.root
        binding.profileAvatar.clipToOutline = true


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)


        // Bind Master to View
        viewModel.databaseMaster.observe(viewLifecycleOwner, { master ->
            binding.master = master
        })


        // Create adapter for RecycleView
        val adapter = ServicesAdapter()
        binding.profileServicesList.adapter = adapter
        viewModel.services.observe(viewLifecycleOwner, {services ->
            adapter.submitServicesList(services)
        })


        return view
    }

}