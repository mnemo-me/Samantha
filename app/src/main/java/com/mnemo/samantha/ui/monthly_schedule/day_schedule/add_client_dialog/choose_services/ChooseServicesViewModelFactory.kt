package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.choose_services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ChooseServicesViewModelFactory(val appointmentId: Long, val clientId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseServicesViewModel::class.java)){
            return ChooseServicesViewModel(appointmentId, clientId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}