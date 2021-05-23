package com.mnemo.samantha.ui.create_profile.select_region

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Master
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectRegionViewModel : ViewModel(){

    @Inject
    lateinit var repository: Repository

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)
    }

    // Create master profile
    fun createProfile(name: String, profession: String, phoneNumber: String, country: String, city: String, currency: String){
        viewModelScope.launch {
            repository.createProfile(Master(name = name, profession = profession, phoneNumber = phoneNumber, country = country, city = city, currency = currency, privateAccess = false))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}