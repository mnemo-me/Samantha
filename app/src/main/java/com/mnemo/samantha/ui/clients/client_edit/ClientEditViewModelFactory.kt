package com.mnemo.samantha.ui.clients.client_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.database.dao.ClientDAO

class ClientEditViewModelFactory(val clientId: Long, val dataSource: ClientDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientEditVewModel::class.java)){
            return ClientEditVewModel(clientId, dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}