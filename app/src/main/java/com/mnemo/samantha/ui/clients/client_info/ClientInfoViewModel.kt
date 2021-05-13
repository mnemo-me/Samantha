package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.*
import javax.inject.Inject

class ClientInfoViewModel(val clientId: Long): ViewModel() {

    @Inject
    lateinit var repository: Repository

    val client : LiveData<Client>

    init {
        DaggerAppComponent.create().inject(this)

        client = repository.getClient(clientId)
    }

    fun removeClient(clientId: Long){
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeClient(clientId)
        }
    }
}