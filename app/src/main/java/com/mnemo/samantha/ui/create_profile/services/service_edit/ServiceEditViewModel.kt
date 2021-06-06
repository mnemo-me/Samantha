package com.mnemo.samantha.ui.create_profile.services.service_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.usecases.AddServiceUseCase
import com.mnemo.samantha.domain.usecases.GetServiceUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServiceEditViewModel(val serviceId: Long) : ViewModel() {

    @Inject
    lateinit var getServiceUseCase : GetServiceUseCase

    @Inject
    lateinit var addServiceUseCase: AddServiceUseCase

    private val _service = MutableLiveData<Service>()
    val service : LiveData<Service>
    get() = _service

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            if (serviceId != 0L) getServiceUseCase.invoke(serviceId).collect { _service.value = it }
        }
    }

    fun updateService(service: Service){
        viewModelScope.launch{
            addServiceUseCase.invoke(service)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}