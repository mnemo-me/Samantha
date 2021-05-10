package com.mnemo.samantha.ui.clients.client_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClientEditVewModel(val clientId: Long, val appointmentId: Long, val repository: Repository) : ViewModel() {

    // Client (back property)
    private var _client = repository.getClient(clientId)
    val client : LiveData<Client>
        get() = _client


    // Update info about client or create new client
    fun updateClientInfo(client : Client){
        CoroutineScope(Dispatchers.IO).launch {
            if (clientId != 0L) {
                repository.updateClientInfo(client)
            }else{
                repository.addClient(Client(name = client.name, phoneNumber = client.phoneNumber))
            }

            if (appointmentId != 0L){
                repository.bookNewClient(appointmentId, 700)
            }
        }
    }


}