package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.usecases.GetClientsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ClientsViewModel : ViewModel() {

    @Inject
    lateinit var getClientsUseCase: GetClientsUseCase

    private val _clients = MutableLiveData<List<Client>>()
    val clients : LiveData<List<Client>>
    get() = _clients

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getClientsUseCase().collect { _clients.value = it }
        }
    }
}