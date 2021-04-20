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

class ClientsFragment : Fragment() {

    private lateinit var binding: FragmentClientsBinding
    private lateinit var viewModel: ClientsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clients, container, false)

        viewModel = ViewModelProvider(this).get(ClientsViewModel::class.java)

        val adapter = ClientsAdapter()
        viewModel.clients.observe(viewLifecycleOwner, {clients ->
            adapter.clients = clients
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


        return binding.root
    }
}