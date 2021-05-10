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
import com.mnemo.samantha.repository.Repository

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val view = binding.root
        binding.profileAvatar.clipToOutline = true


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = ProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)


        // Bind Master to View
        viewModel.master.observe(viewLifecycleOwner, {master ->
            binding.master = master
        })


        return view
    }

}