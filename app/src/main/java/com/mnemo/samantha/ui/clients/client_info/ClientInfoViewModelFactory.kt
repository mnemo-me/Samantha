package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client

class ClientInfoViewModelFactory(val clientId: Long, val dataSource: ClientDAO): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientInfoViewModel::class.java)){
            return ClientInfoViewModel(clientId, dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}