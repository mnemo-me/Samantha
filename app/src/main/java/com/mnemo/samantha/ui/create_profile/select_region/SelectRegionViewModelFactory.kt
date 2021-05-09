package com.mnemo.samantha.ui.create_profile.select_region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class SelectRegionViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectRegionViewModel::class.java)){
            return SelectRegionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}