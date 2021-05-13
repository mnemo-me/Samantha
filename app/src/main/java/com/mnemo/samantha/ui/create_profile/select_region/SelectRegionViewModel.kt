package com.mnemo.samantha.ui.create_profile.select_region

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Master
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectRegionViewModel : ViewModel(){

    @Inject
    lateinit var repository: Repository

    init {
        DaggerAppComponent.create().inject(this)
    }

    // Create master profile
    fun createProfile(name: String, profession: String, phoneNumber: String, country: String, city: String, currency: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.createProfile(Master(name = name, profession = profession, phoneNumber = phoneNumber, country = country, city = city, currency = currency, privateAccess = false))
        }
    }
}