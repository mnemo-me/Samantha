package com.mnemo.samantha.ui.create_profile.select_region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectRegionVIewModelFactory(val masterId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectRegionViewModel::class.java)){
            return SelectRegionViewModel(masterId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}