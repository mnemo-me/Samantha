package com.mnemo.samantha.ui.create_profile.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Service
import javax.inject.Inject

class ServicesViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val services: LiveData<List<Service>>

    val currency: LiveData<String>

    init {
        DaggerAppComponent.create().inject(this)

        services = repository.getServiceList()

        currency = repository.getCurrency()
    }
}