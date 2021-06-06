package com.mnemo.samantha.ui.create_profile.select_region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.usecases.CreateProfileUseCase
import com.mnemo.samantha.domain.usecases.GetMasterUseCase
import com.mnemo.samantha.domain.usecases.UpdateProfileRegionInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectRegionViewModel : ViewModel(){

    @Inject
    lateinit var getMasterUseCase: GetMasterUseCase

    @Inject
    lateinit var createProfileUseCase: CreateProfileUseCase

    @Inject
    lateinit var updateProfileRegionInfoUseCase: UpdateProfileRegionInfoUseCase

    private var _master = MutableLiveData<Master>()
    val master : LiveData<Master>
    get() = _master

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getMasterUseCase.invoke().collect { _master.value = it }
        }
    }

    fun createProfile(name: String, profession: String, phoneNumber: String, country: String, city: String, currency: String){
        viewModelScope.launch {
            val master = Master(name = name, profession = profession, phoneNumber = phoneNumber, country = country, city = city, currency = currency, privateAccess = false)
            createProfileUseCase.invoke(master)
        }
    }

    fun updateProfileRegionInfo(id: Long, country: String, city: String, currency: String){
        viewModelScope.launch {
            updateProfileRegionInfoUseCase.invoke(id, country, city, currency)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}