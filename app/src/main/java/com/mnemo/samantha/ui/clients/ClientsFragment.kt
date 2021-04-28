package com.mnemo.samantha.ui.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentClientsBinding
import com.mnemo.samantha.repository.database.SamanthaDatabase

class ClientsFragment : Fragment() {

    private lateinit var binding: FragmentClientsBinding
    private lateinit var viewModel: ClientsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // View binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clients, container, false)


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val dataSource = SamanthaDatabase.getInstance(application).clientDao

        val viewModelFactory = ClientsViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ClientsViewModel::class.java)

        binding.lifecycleOwner = this

        binding.clientsViewModel = viewModel





        val adapter = ClientsAdapter()

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
            adapter.clients = clients
        })


        return binding.root
    }
}