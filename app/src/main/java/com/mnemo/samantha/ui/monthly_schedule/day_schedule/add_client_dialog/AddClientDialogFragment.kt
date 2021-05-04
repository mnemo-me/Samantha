package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.DialogFragmentAddClientBinding
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.ui.clients.ClientsAdapter


class AddClientDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFragmentAddClientBinding
    private lateinit var viewModel: AddClientDialogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_fragment_add_client, container, false)


        // Create ViewModel via Factory
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val appointmentId = requireArguments().getLong("appointment_id")

        val viewModelFactory = AddClientDialogVIewModelFactory(appointmentId, repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddClientDialogViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = ClientsAdapter()

        adapter.addNewClientClickListener = ClientsAdapter.AddNewClientClickListener {  }

        adapter.clickListener = ClientsAdapter.ClientClickListener {clientId ->
            viewModel.bookClient(clientId, 700)
            dismiss()
        }

        val layoutManager = GridLayoutManager(context, 3)

        binding.addClientList.layoutManager = layoutManager

        binding.addClientList.adapter = adapter

        viewModel.clients.observe(viewLifecycleOwner, {clients ->
            adapter.addHeaderAndSubmitList(clients)
        })

        return binding.root
    }
}