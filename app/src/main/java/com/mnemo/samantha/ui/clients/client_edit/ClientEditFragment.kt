package com.mnemo.samantha.ui.clients.client_edit

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentClientEditBinding
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.repository.database.entity.Client

class ClientEditFragment : Fragment() {

    private lateinit var binding: FragmentClientEditBinding
    private lateinit var viewModel: ClientEditVewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_client_edit, container, false)
        val view = binding.root
        binding.clientEditAvatar.clipToOutline = true
        binding.clientEditTextPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        // Create ViewModel via Factory
        val application = requireNotNull(this.activity).application

        val clientId = requireArguments().get("client_id") as Long

        val dataSource = SamanthaDatabase.getInstance(application).clientDao

        val viewModelFactory = ClientEditViewModelFactory(clientId, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ClientEditVewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        // Bind client
        viewModel.client.observe(viewLifecycleOwner, {client ->
            binding.client = client
        })


        // Button 'Done' click listener
        // Update client info or create new client based on clientId
        binding.clientEditDoneButton.setOnClickListener{
            val clientName = binding.clientEditTextName.text.toString()
            val clientPhoneNumber = binding.clientEditTextPhoneNumber.text.toString()
            viewModel.updateClientInfo(Client(clientId, clientName, clientPhoneNumber))

            view.findNavController().navigateUp()

            if (clientId != 0L){
                Snackbar.make(view, getText(R.string.client_info_updated), Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(view, getText(R.string.client_added_to_list), Snackbar.LENGTH_SHORT).show()
            }
        }

        // Button 'Back' click listener
        binding.clientEditBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }


        return view
    }


}