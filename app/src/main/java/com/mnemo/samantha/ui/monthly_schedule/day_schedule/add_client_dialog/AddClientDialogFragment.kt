package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.DialogFragmentAddClientBinding
import com.mnemo.samantha.ui.clients.ClientsAdapter


class AddClientDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFragmentAddClientBinding
    private lateinit var viewModel: AddClientDialogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_fragment_add_client, container, false)
        val view = binding.root


        // Create ViewModel via Factory
        val appointmentId = requireArguments().getLong("appointment_id")

        val viewModelFactory = AddClientDialogVIewModelFactory(appointmentId)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddClientDialogViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = ClientsAdapter()
        adapter.pictureFolder = view.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        adapter.addNewClientClickListener = ClientsAdapter.AddNewClientClickListener {
            parentFragment?.view?.findNavController()?.navigate(R.id.action_addClientDialogFragment_to_clientEditFragment, bundleOf("appointment_id" to appointmentId))
        }

        adapter.clickListener = ClientsAdapter.ClientClickListener {clientId ->
            parentFragment?.view?.findNavController()?.navigate(R.id.action_addClientDialogFragment_to_chooseServicesFragment, bundleOf("appointment_id" to appointmentId, "client_id" to clientId))
        }

        val layoutManager = GridLayoutManager(context, 3)

        binding.addClientList.layoutManager = layoutManager

        binding.addClientList.adapter = adapter

        viewModel.clients.observe(viewLifecycleOwner, {clients ->
            adapter.addHeaderAndSubmitList(clients)
        })

        return view
    }
}