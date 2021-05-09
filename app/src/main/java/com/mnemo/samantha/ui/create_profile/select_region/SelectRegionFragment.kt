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
import com.mnemo.samantha.repository.Repository

class SelectRegionFragment : Fragment() {

    private lateinit var binding: FragmentSelectRegionBinding
    private lateinit var viewModel: SelectRegionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_region, container, false)
        val view = binding.root


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = SelectRegionViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SelectRegionViewModel::class.java)


        // Back button click listener
        binding.selectRegionBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Next button click listener
        binding.selectRegionNextButton.setOnClickListener{

            val masterName = requireArguments().getString("master_name")!!
            val masterProfession = requireArguments().getString("master_profession")!!
            val masterPhoneNumber = requireArguments().getString("master_phone_number")!!
            val masterCountry = binding.selectRegionCountry.text.toString()
            val masterCity = binding.selectRegionCity.text.toString()
            val masterCurrency = binding.selectRegionCurrency.text.toString()


            viewModel.createProfile(masterName, masterProfession, masterPhoneNumber, masterCountry, masterCity, masterCurrency)
            view.findNavController().navigate(R.id.action_selectRegionFragment_to_servicesFragment)
        }

        return view

    }

}