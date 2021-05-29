package com.mnemo.samantha.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Master
import com.mnemo.samantha.domain.Service
import com.mnemo.samantha.repository.Repository
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val databaseMaster: LiveData<Master>

    val services: LiveData<List<Service>>

    init {
        DaggerAppComponent.create().inject(this)

        databaseMaster = repository.master

        services = repository.services
    }

    fun getMasterAvatarPath(masterId: Long) = repository.getMasterAvatarPath(masterId)
}