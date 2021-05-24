package com.mnemo.samantha.ui.clients.client_edit

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Client
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientEditVewModel(val clientId: Long, val appointmentId: Long) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val client : LiveData<Client>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        client = repository.getClient(clientId)
    }

    // Update info about client or create new client
    fun updateClientInfo(clientName: String, clientPhoneNumber: String){
        viewModelScope.launch {

            val client = Client(clientId, clientName, clientPhoneNumber)

            if (appointmentId == 0L) {
                repository.addClient(client)
            }else{
                repository.bookNewClient(appointmentId, client, 700)
            }
        }
    }

    fun updateClientAvatar(bitmap: Bitmap, clientId: Long){

        viewModelScope.launch {
            repository.saveClientAvatar(bitmap, clientId)
        }
    }

    fun getClientAvatarPath(clientId: Long) = repository.getClientAvatarPath(clientId)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}