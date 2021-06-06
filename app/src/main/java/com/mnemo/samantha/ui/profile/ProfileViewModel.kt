package com.mnemo.samantha.ui.profile

import androidx.lifecycle.*
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.usecases.GetMasterAvatarPathUseCase
import com.mnemo.samantha.domain.usecases.GetMasterUseCase
import com.mnemo.samantha.domain.usecases.GetServicesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject
    lateinit var getMasterUseCase : GetMasterUseCase

    @Inject
    lateinit var getMasterAvatarPathUseCase: GetMasterAvatarPathUseCase

    @Inject
    lateinit var getServicesUseCase: GetServicesUseCase

    private var _master = MutableLiveData<Master>()
    val master : LiveData<Master>
    get() = _master

    private val _services = MutableLiveData<List<Service>>()
    val services : LiveData<List<Service>>
    get() = _services

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getMasterUseCase.invoke().collect { _master.value = it }
            getServicesUseCase.invoke().collect { _services.value = it }
        }
    }

    fun getMasterAvatarPath(masterId: Long) = getMasterAvatarPathUseCase.invoke(masterId)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}