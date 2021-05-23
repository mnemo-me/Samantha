package com.mnemo.samantha.ui.create_profile.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Service
import com.mnemo.samantha.repository.Repository
import javax.inject.Inject

class ServicesViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val services: LiveData<List<Service>>

    val currency: LiveData<String>

    init {
        DaggerAppComponent.create().inject(this)

        services = repository.services

        currency = repository.getCurrency()
    }
}