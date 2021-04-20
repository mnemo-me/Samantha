package com.mnemo.samantha.ui.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mnemo.samantha.R
import com.mnemo.samantha.data.Client
import com.mnemo.samantha.databinding.FragmentClientsBinding

class ClientsFragment : Fragment() {

    private lateinit var binding: FragmentClientsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clients, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val clients = mutableListOf<ClientsAdapter.DataItem>()

        clients.add(ClientsAdapter.DataItem.Header)

        for (i in 1..40){
            clients.add(ClientsAdapter.DataItem.ClientItem(Client(123,"Samantha $i", "")))
        }

        val adapter = ClientsAdapter()
        adapter.clients = clients

        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 3
                    else -> 1
                }
            }

        }
        binding.clientsList.layoutManager = layoutManager


        binding.clientsList.adapter = adapter
    }
}