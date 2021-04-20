package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.data.Client

class ClientsViewModel : ViewModel() {

    private val _clients = MutableLiveData<MutableList<ClientsAdapter.DataItem>>()
    val clients : LiveData<MutableList<ClientsAdapter.DataItem>>
    get() = _clients

    init {
        _clients.value = mutableListOf()
        _clients.value?.add(ClientsAdapter.DataItem.Header)

        for (i in 1..40){
            _clients.value?.add(ClientsAdapter.DataItem.ClientItem(Client(123,"Samantha $i", "")))
        }
    }
}