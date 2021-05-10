package com.mnemo.samantha.ui.create_profile.services.service_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class ServiceEditViewModelFactory(val serviceId: Long, val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceEditViewModel::class.java)){
            return ServiceEditViewModel(serviceId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}