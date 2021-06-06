package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.repositories.Repository
import com.mnemo.samantha.domain.usecases.GetClientsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.io.File
import javax.inject.Inject

class ClientsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var getClientsUseCase: GetClientsUseCase

    private val _clients = MutableLiveData<List<Client>>()
    val clients : LiveData<List<Client>>
    get() = _clients

    val storagePath: File

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getClientsUseCase.invoke().collect { _clients.value = it }
        }
        storagePath = repository.getStoragePath()!!
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}