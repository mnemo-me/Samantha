package com.mnemo.samantha.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Master
import com.mnemo.samantha.repository.database.entity.Service
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val master: LiveData<Master>

    val services: LiveData<List<Service>>

    init {
        DaggerAppComponent.create().inject(this)

        master = repository.getMaster()

        services = repository.getServiceList()
    }
}