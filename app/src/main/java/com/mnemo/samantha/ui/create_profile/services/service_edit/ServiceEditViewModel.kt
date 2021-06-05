package com.mnemo.samantha.ui.create_profile.services.service_edit

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

class ServiceEditViewModel(val serviceId: Long) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val databaseService: LiveData<Service>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        databaseService = repository.getService(serviceId)
    }



    fun updateService(service: Service){
        viewModelScope.launch{
            repository.addService(Service(id = serviceId, name = service.name, price = service.price, timeToComplete = service.timeToComplete))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}