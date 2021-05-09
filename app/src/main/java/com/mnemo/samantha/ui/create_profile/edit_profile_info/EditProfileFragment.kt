package com.mnemo.samantha.ui.create_profile.edit_profile_info

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
import com.mnemo.samantha.databinding.FragmentEditProfileBinding
import com.mnemo.samantha.repository.Repository


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change back button behavior
        requireActivity().onBackPressedDispatcher.addCallback(this){

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit_profile, container, false)
        val view = binding.root
        binding.editProfileAvatar.clipToOutline = true


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = EditProfileViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EditProfileViewModel::class.java)


        // Close button click listener
        binding.editProfileCloseButton.setOnClickListener{

        }


        // Next button click listener
        binding.editProfileNextButton.setOnClickListener {

            val masterName = binding.editProfileName.text.toString()
            val masterProfession = binding.editProfileProfession.text.toString()
            val masterPhoneNumber = binding.editProfilePhoneNumber.text.toString()

            view.findNavController().navigate(R.id.action_editProfileFragment_to_selectRegionFragment,
            bundleOf("master_name" to masterName, "master_profession" to masterProfession, "master_phone_number" to masterPhoneNumber))
        }

        return view
    }


}