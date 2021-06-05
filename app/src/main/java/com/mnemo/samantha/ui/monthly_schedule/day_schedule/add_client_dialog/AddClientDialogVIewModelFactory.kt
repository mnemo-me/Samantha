package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddClientDialogVIewModelFactory(val appointmentId: Long) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddClientDialogViewModel::class.java)){
            return AddClientDialogViewModel(appointmentId) as T
        }

        throw IllegalArgumentException("Unknown viewModel class")
    }
}