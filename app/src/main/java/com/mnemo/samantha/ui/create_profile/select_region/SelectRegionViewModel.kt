package com.mnemo.samantha.ui.create_profile.select_region

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectRegionViewModel : ViewModel(){

    @Inject
    lateinit var repository: Repository

    val master: LiveData<Master>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        master = repository.master
    }

    // Create master profile
    fun createProfile(name: String, profession: String, phoneNumber: String, country: String, city: String, currency: String){
        viewModelScope.launch {
            repository.createProfile(Master(name = name, profession = profession, phoneNumber = phoneNumber, country = country, city = city, currency = currency, privateAccess = false))
        }
    }

    fun updateProfileRegionInfo(id: Long, country: String, city: String, currency: String){
        viewModelScope.launch {
            repository.updateProfileRegionInfo(id, country, city, currency)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}