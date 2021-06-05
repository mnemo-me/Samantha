package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.choose_services

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseServicesViewModel(val appointmentId: Long, val clientId: Long) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val services: LiveData<List<Service>>

    private val checkedServicesList = mutableListOf<Service>()

    var totalCost = 0L
    var totalTime = 0

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        services = repository.services
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
            repository.bookClient(appointmentId, clientId, checkedServicesList, totalCost, totalTime)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}