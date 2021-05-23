package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClientInfoViewModelFactory(val clientId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientInfoViewModel::class.java)){
            return ClientInfoViewModel(clientId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}