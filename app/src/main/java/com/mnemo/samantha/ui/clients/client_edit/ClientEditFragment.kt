package com.mnemo.samantha.ui.clients.client_edit

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.mnemo.samantha.repository.database.entity.Client

private const val REQUEST_IMAGE_CAPTURE = 0
private const val REQUEST_IMAGE_PICK = 1

class ClientEditFragment : Fragment() {

    private lateinit var binding: FragmentClientEditBinding
    private lateinit var viewModel: ClientEditVewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_client_edit, container, false)
        val view = binding.root
        binding.clientEditAvatar.clipToOutline = true
        binding.clientEditTextPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        // Get arguments
        val clientId = requireArguments().getLong("client_id")
        val appointmentId = requireArguments().getLong("appointment_id")


        // Create ViewModel via Factory
        val viewModelFactory = ClientEditViewModelFactory(clientId, appointmentId)

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

        // Client avatar click listener
        binding.clientEditAvatar.setOnClickListener{

            val items = arrayOf(getString(R.string.take_a_photo), getString(R.string.choose_from_gallery))

            AlertDialog.Builder(context)
                .setItems(items) { dialogInterface, item ->

                    when (items[item]) {
                        getString(R.string.take_a_photo) -> {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                        }
                        getString(R.string.choose_from_gallery) -> {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                            startActivityForResult(intent, REQUEST_IMAGE_PICK)
                        }
                    }

                }.show()
        }


        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK ) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.clientEditAvatar.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    val imageUri = data?.data
                    binding.clientEditAvatar.setImageURI(imageUri)
                }
            }
        }

    }

}