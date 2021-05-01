package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.*

class ClientInfoViewModel(val clientId: Long, val database: ClientDAO): ViewModel() {

    // Client (back property)
    private var _client = database.get(clientId)
    val client : LiveData<Client>
    get() = _client

    // Coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    fun removeClient(key: Long){
        CoroutineScope(Dispatchers.IO).launch {
            database.remove(key)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}