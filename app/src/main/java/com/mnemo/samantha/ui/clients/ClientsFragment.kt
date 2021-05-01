package com.mnemo.samantha.ui.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentClientsBinding
import com.mnemo.samantha.repository.database.SamanthaDatabase

class ClientsFragment : Fragment() {

    private lateinit var binding: FragmentClientsBinding
    private lateinit var viewModel: ClientsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clients, container, false)
        val view = binding.root


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val dataSource = SamanthaDatabase.getInstance(application).clientDao

        val viewModelFactory = ClientsViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ClientsViewModel::class.java)

        binding.lifecycleOwner = this

        binding.clientsViewModel = viewModel


        // Create adapter for RecycleView
        val adapter = ClientsAdapter(
            ClientsAdapter.AddNewClientClickListener { ->
                view.findNavController().navigate(R.id.action_navigation_clients_to_clientEditFragment)
            },
                ClientsAdapter.ClientClickListener {clientId ->
                    view.findNavController().navigate(R.id.action_navigation_clients_to_clientInfoFragment, bundleOf("client_id" to clientId))
                })

        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 3
                    else -> 1
                }
            }

        }
        binding.clientsList.layoutManager = layoutManager

        binding.clientsList.adapter = adapter

        viewModel.clients.observe(viewLifecycleOwner, {clients ->
            adapter.addHeaderAndSubmitList(clients)
        })


        return view
    }

}