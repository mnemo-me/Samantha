package com.mnemo.samantha.ui.clients.client_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientEditVewModel(val clientId: Long, val appointmentId: Long) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val client : LiveData<Client>

    init {
        DaggerAppComponent.create().inject(this)

        client = repository.getClient(clientId)
    }

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