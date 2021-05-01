package com.mnemo.samantha.ui.clients.client_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentClientInfoBinding
import com.mnemo.samantha.repository.database.SamanthaDatabase


class ClientInfoFragment : Fragment() {

    private lateinit var binding: FragmentClientInfoBinding
    private lateinit var viewModel: ClientInfoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_client_info, container, false)
        val view = binding.root
        binding.clientInfoAvatar.clipToOutline = true


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val clientId = requireArguments().get("client_id") as Long

        val dataSource = SamanthaDatabase.getInstance(application).clientDao

        val viewModelFactory = ClientInfoViewModelFactory(clientId, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ClientInfoViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        // Bind client
        viewModel.client.observe(viewLifecycleOwner, {client ->
            binding.client = client
        })


        // Button 'Edit' click listener
        binding.clientInfoEditButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_clientInfoFragment_to_clientEditFragment, bundleOf("client_id" to clientId))
        }

        // Button 'Delete' click listener
        binding.clientInfoRemove.setOnClickListener{
            viewModel.removeClient(clientId)
            view.findNavController().navigateUp()
            Snackbar.make(view, getText(R.string.client_removed_from_list), Snackbar.LENGTH_SHORT).show()
        }

        // Button 'Back' click listener
        binding.clientInfoBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        return view
    }


}