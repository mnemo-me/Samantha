package com.mnemo.samantha.ui.profile

import android.app.AlertDialog
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
import com.mnemo.samantha.ui.loadImage


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
            binding.profileAvatar.loadImage(viewModel.getMasterAvatarPath(master.id))

            // Edit profile button click listener
            binding.profileEditButton.setOnClickListener{
                val items = arrayOf(getString(R.string.edit_profile_info), getString(R.string.edit_region), getString(R.string.edit_services))

                AlertDialog.Builder(context)
                    .setItems(items){dialogInterface, item ->
                        when (items[item]){
                            getString(R.string.edit_profile_info) -> view.findNavController().navigate(R.id.action_navigation_profile_to_profileEditFragment, bundleOf("master_id" to master.id))
                            getString(R.string.edit_region) -> view.findNavController().navigate(R.id.action_navigation_profile_to_selectRegionFragment, bundleOf("master_id" to master.id))
                            getString(R.string.edit_services) -> view.findNavController().navigate(R.id.action_navigation_profile_to_servicesFragment, bundleOf("master_id" to master.id))
                        }
                    }.show()
            }
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