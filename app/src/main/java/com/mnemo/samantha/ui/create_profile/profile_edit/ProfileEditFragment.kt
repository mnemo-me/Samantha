package com.mnemo.samantha.ui.create_profile.profile_edit

import android.app.Activity
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
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentProfileEditBinding
import com.mnemo.samantha.ui.loadImage
import com.mnemo.samantha.ui.loadImageDrawable


private const val REQUEST_IMAGE_CAPTURE = 0
private const val REQUEST_IMAGE_PICK = 1


class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var viewModel: ProfileEditViewModel

    private var newImageUri: Uri? = null
    private var avatarBitmap: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_profile_edit, container, false)
        val view = binding.root
        binding.profileEditAvatar.clipToOutline = true
        binding.profileEditPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        // Get master id
        val masterId = requireArguments().getLong("master_id")

        // Create ViewModel via Factory
        val viewModelFactory = ProfileEditViewModelFactory(masterId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileEditViewModel::class.java)



        // Bind master
        if (masterId != 0L){

            viewModel.master.observe(viewLifecycleOwner){master ->
                binding.master = master
                binding.profileEditAvatar.loadImage(viewModel.getMasterAvatarPath(masterId))
            }

            binding.profileEditTitle.text = view.resources.getString(R.string.edit_profile)
            binding.profileEditNextButton.setImageResource(R.drawable.outline_done_black_24)

        }else{

            binding.profileEditAvatar.loadImageDrawable(resources.getDrawable(R.drawable.empty_profile))

        }


        // Change back button behavior
        if (masterId == 0L) requireActivity().onBackPressedDispatcher.addCallback(this){}


        // Close button click listener
        binding.profileEditCloseButton.setOnClickListener{
            if (masterId != 0L){
                view.findNavController().navigateUp()
            }else{

            }
        }


        // Next button click listener
        binding.profileEditNextButton.setOnClickListener {

            val masterName = binding.profileEditName.text.toString()
            val masterProfession = binding.profileEditProfession.text.toString()
            val masterPhoneNumber = binding.profileEditPhoneNumber.text.toString()

            // Prepare avatar
            if (avatarBitmap == null){
                if (newImageUri != null){
                    avatarBitmap = viewModel.getBitmapFromUri(view.context, newImageUri!!)
                }else{
                    if (masterId == 0L) {
                        avatarBitmap = viewModel.getDefaultProfileBitmap(view.context)
                    }
                }
            }
            viewModel.saveMasterAvatar(avatarBitmap)

            if (masterId != 0L){
                viewModel.updateProfileInfo(masterId, masterName, masterProfession, masterPhoneNumber)
                view.findNavController().navigateUp()
            }else {
                view.findNavController().navigate(
                    R.id.action_profileEditFragmentCreateProfile_to_selectRegionFragmentCreateProfile,
                    bundleOf(
                        "master_name" to masterName,
                        "master_profession" to masterProfession,
                        "master_phone_number" to masterPhoneNumber
                    )
                )
            }
        }


        // Profile avatar click listener
        binding.profileEditAvatar.setOnClickListener{

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

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    newImageUri = null
                    avatarBitmap = data?.extras?.get("data") as Bitmap
                    binding.profileEditAvatar.setImageBitmap(avatarBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    avatarBitmap = null
                    newImageUri = data?.data
                    binding.profileEditAvatar.setImageURI(newImageUri)
                }
            }
        }
    }

}