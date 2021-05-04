package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class AddClientDialogVIewModelFactory(val appointmentId: Long, val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddClientDialogViewModel::class.java)){
            return AddClientDialogViewModel(appointmentId, repository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class")
    }
}