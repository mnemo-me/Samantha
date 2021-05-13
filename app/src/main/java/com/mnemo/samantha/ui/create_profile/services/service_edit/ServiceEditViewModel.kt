package com.mnemo.samantha.ui.create_profile.services.service_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServiceEditViewModel(val serviceId: Long) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val service: LiveData<Service>

    init {
        DaggerAppComponent.create().inject(this)

        service = repository.getService(serviceId)
    }



    fun updateService(service: Service){
        CoroutineScope(Dispatchers.IO).launch{
            if (serviceId != 0L){
                repository.updateService(service)
            }else{
                repository.addService(Service(name = service.name, price = service.price, timeToComplete = service.timeToComplete))
            }
        }
    }
}