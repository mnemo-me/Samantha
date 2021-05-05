package com.mnemo.samantha.ui.clients.client_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class ClientEditViewModelFactory(val clientId: Long, val appointmentId: Long, val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientEditVewModel::class.java)){
            return ClientEditVewModel(clientId, appointmentId, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}