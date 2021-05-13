package com.mnemo.samantha.ui.create_profile.profile_edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentProfileEditBinding


class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var viewModel: ProfileEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change back button behavior
        requireActivity().onBackPressedDispatcher.addCallback(this){

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_profile_edit, container, false)
        val view = binding.root
        binding.profileEditAvatar.clipToOutline = true


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(ProfileEditViewModel::class.java)


        // Close button click listener
        binding.profileEditCloseButton.setOnClickListener{

        }


        // Next button click listener
        binding.profileEditNextButton.setOnClickListener {

            val masterName = binding.profileEditName.text.toString()
            val masterProfession = binding.profileEditProfession.text.toString()
            val masterPhoneNumber = binding.profileEditPhoneNumber.text.toString()

            view.findNavController().navigate(R.id.action_profileEditFragment_to_selectRegionFragment,
            bundleOf("master_name" to masterName, "master_profession" to masterProfession, "master_phone_number" to masterPhoneNumber))
        }

        return view
    }


}