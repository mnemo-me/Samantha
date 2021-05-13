package com.mnemo.samantha.ui.create_profile.services.service_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ServiceEditViewModelFactory(val serviceId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceEditViewModel::class.java)){
            return ServiceEditViewModel(serviceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}