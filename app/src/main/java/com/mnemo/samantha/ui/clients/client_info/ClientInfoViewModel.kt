package com.mnemo.samantha.ui.clients.client_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.usecases.GetClientAppointmentsUseCase
import com.mnemo.samantha.domain.usecases.GetClientAvatarPathUseCase
import com.mnemo.samantha.domain.usecases.GetClientUseCase
import com.mnemo.samantha.domain.usecases.RemoveClientUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ClientInfoViewModel(val clientId: Long): ViewModel() {

    @Inject
    lateinit var getClientUseCase: GetClientUseCase

    @Inject
    lateinit var getClientAvatarPathUseCase: GetClientAvatarPathUseCase

    @Inject
    lateinit var removeClientUseCase: RemoveClientUseCase

    @Inject
    lateinit var getClientAppointmentsUseCase: GetClientAppointmentsUseCase

    private val _client = MutableLiveData<Client>()
    val client : LiveData<Client>
    get() = _client

    private val _clientAppointments = MutableLiveData<List<Appointment>>()
    val clientAppointments : LiveData<List<Appointment>>
    get() = _clientAppointments

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getClientUseCase(clientId).collect { _client.value = it }
        }
        viewModelScope.launch {
            getClientAppointmentsUseCase(clientId).collect{ _clientAppointments.value = it}
        }
    }

    fun removeClient(){
        viewModelScope.launch {
            removeClientUseCase(clientId)
        }
    }

    fun getClientAvatarPath(clientId: Long) = getClientAvatarPathUseCase(clientId)

}