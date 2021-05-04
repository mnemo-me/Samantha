package com.mnemo.samantha.ui.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.dao.ClientDAO

class ClientsViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ClientsViewModel::class.java)){
            return ClientsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}