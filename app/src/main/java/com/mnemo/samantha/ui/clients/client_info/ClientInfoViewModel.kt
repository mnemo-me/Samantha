package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Client
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class ClientInfoViewModel(val clientId: Long): ViewModel() {

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

    fun removeClient(){
        viewModelScope.launch {
            repository.removeClient(clientId)
        }
    }

    fun getClientAvatarPath(clientId: Long) = repository.getClientAvatarPath(clientId)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}