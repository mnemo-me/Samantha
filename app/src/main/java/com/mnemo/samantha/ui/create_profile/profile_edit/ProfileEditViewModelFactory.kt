package com.mnemo.samantha.ui.create_profile.profile_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class ProfileEditViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileEditViewModel::class.java)){
            return ProfileEditViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}