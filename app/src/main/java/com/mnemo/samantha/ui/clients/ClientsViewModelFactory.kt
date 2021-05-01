package com.mnemo.samantha.ui.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.database.dao.ClientDAO

class ClientsViewModelFactory(private val dataSource: ClientDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ClientsViewModel::class.java)){
            return ClientsViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}