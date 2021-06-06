package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.repositories.Repository
import com.mnemo.samantha.domain.usecases.GetClientAvatarPathUseCase
import com.mnemo.samantha.domain.usecases.GetClientUseCase
import com.mnemo.samantha.domain.usecases.RemoveClientUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ClientInfoViewModel(val clientId: Long): ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var getClientUseCase: GetClientUseCase

    @Inject
    lateinit var getClientAvatarPathUseCase: GetClientAvatarPathUseCase

    @Inject
    lateinit var removeClientUseCase: RemoveClientUseCase

    private val _client = MutableLiveData<Client>()
    val client : LiveData<Client>
    get() = _client


    val clientAppointments: LiveData<List<Appointment>>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getClientUseCase.invoke(clientId).collect { _client.value = it }
        }
        clientAppointments = repository.getClientAppointments(clientId)
    }

    fun removeClient(){
        viewModelScope.launch {
            removeClientUseCase.invoke(clientId)
        }
    }

    fun getClientAvatarPath(clientId: Long) = getClientAvatarPathUseCase.invoke(clientId)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}