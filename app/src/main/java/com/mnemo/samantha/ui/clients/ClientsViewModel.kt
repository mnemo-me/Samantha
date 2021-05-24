package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Client
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

class ClientsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val clients : LiveData<List<Client>>

    val storagePath: File

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        DaggerAppComponent.create().inject(this)

        clients = repository.clients
        storagePath = repository.getStoragePath()!!
    }


    // Functions that can launch from UI interactions
    fun addClient(client: Client){
        viewModelScope.launch {
            repository.addClient(client)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}