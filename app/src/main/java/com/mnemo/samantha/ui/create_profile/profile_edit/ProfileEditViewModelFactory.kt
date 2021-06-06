package com.mnemo.samantha.ui.create_profile.profile_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileEditViewModelFactory(val masterId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileEditViewModel::class.java)){
            return ProfileEditViewModel(masterId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}