package com.mnemo.samantha.ui.create_profile.services.service_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.usecases.AddServiceUseCase
import com.mnemo.samantha.domain.usecases.GetServiceUseCase
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

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            if (serviceId != 0L) getServiceUseCase(serviceId).collect { _service.value = it }
        }
    }

    fun updateService(service: Service){
        viewModelScope.launch{
            addServiceUseCase(service)
        }
    }

}