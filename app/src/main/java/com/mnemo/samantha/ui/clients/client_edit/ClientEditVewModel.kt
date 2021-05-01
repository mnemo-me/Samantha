package com.mnemo.samantha.ui.clients.client_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClientEditVewModel(val clientId: Long, val database: ClientDAO) : ViewModel() {

    // Client (back property)
    private var _client = database.get(clientId)
    val client : LiveData<Client>
        get() = _client

    // Coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {

        uiScope.launch {

        }
    }


    // Update info about client or create new client
    fun updateClientInfo(client : Client){
        CoroutineScope(Dispatchers.IO).launch {
            if (clientId != 0L) {
                database.update(client)
            }else{
                database.insert(Client(clientName = client.clientName, clientPhoneNumber = client.clientPhoneNumber))
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}