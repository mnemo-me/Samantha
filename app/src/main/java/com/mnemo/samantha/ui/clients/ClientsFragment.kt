package com.mnemo.samantha.ui.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentClientsBinding


class ClientsFragment : Fragment() {

    private lateinit var binding: FragmentClientsBinding
    private lateinit var viewModel: ClientsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clients, container, false)
        val view = binding.root


        // Create ViewModel and bind it to View
        viewModel = ViewModelProvider(this).get(ClientsViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        // Create adapter for RecycleView
        val adapter = ClientsAdapter()

        adapter.pictureFolder = viewModel.storagePath

        adapter.addNewClientClickListener = ClientsAdapter.AddNewClientClickListener { ->
            view.findNavController().navigate(R.id.action_navigation_clients_to_clientEditFragment)
        }

        adapter.clickListener = ClientsAdapter.ClientClickListener {client ->
            view.findNavController().navigate(R.id.action_navigation_clients_to_clientInfoFragment, bundleOf("client_id" to client.id))
        }

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