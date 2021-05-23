package com.mnemo.samantha.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentProfileBinding


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


        return view
    }

}