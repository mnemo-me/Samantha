package com.mnemo.samantha.ui.create_profile.select_region

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentSelectRegionBinding


class SelectRegionFragment : Fragment() {

    private lateinit var binding: FragmentSelectRegionBinding
    private lateinit var viewModel: SelectRegionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_region, container, false)
        val view = binding.root


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(SelectRegionViewModel::class.java)


        // Bind master region info
        val masterId = requireArguments().getLong("master_id")

        if (masterId != 0L){
            viewModel.master.observe(viewLifecycleOwner){master ->
                binding.master = master
            }

            binding.selectRegionBackButton.setImageResource(R.drawable.outline_close_black_24)
            binding.selectRegionNextButton.setImageResource(R.drawable.outline_done_black_24)
        }


        // Back button click listener
        binding.selectRegionBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Next button click listener
        binding.selectRegionNextButton.setOnClickListener{

            val masterCountry = binding.selectRegionCountry.text.toString()
            val masterCity = binding.selectRegionCity.text.toString()
            val masterCurrency = binding.selectRegionCurrency.text.toString()

            if (masterId != 0L){

                viewModel.updateProfileRegionInfo(masterId, masterCountry, masterCity, masterCurrency)
                view.findNavController().navigateUp()

            }else{

                val masterName = requireArguments().getString("master_name")!!
                val masterProfession = requireArguments().getString("master_profession")!!
                val masterPhoneNumber = requireArguments().getString("master_phone_number")!!

                viewModel.createProfile(masterName, masterProfession, masterPhoneNumber, masterCountry, masterCity, masterCurrency)
                view.findNavController().navigate(R.id.action_selectRegionFragmentCreateProfile_to_servicesFragmentCreateProfile)
            }
        }

        return view

    }

}