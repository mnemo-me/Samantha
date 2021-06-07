package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.choose_services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.usecases.BookClientUseCase
import com.mnemo.samantha.domain.usecases.GetServicesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseServicesViewModel(val appointmentId: Long, val clientId: Long) : ViewModel() {

    @Inject
    lateinit var getServicesUseCase: GetServicesUseCase

    @Inject
    lateinit var bookClientUseCase: BookClientUseCase

    private val _services = MutableLiveData<List<Service>>()
    val services : LiveData<List<Service>>
    get() = _services

    private val checkedServicesList = mutableListOf<Service>()

    var totalCost = 0L
    var totalTime = 0

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getServicesUseCase().collect { _services.value = it }
        }
    }

    fun updateCheckedServices(service: Service, isChecked: Boolean){
        if (isChecked){
            checkedServicesList.add(service)
            totalCost += service.price
            totalTime += service.timeToComplete
        }else{
            checkedServicesList.remove(service)
            totalCost -= service.price
            totalTime -= service.timeToComplete
        }
    }

    fun bookClient(){
        viewModelScope.launch {
            bookClientUseCase(appointmentId, clientId, checkedServicesList, totalCost, totalTime)
        }
    }

}