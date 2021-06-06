package com.mnemo.samantha.ui.profile

import androidx.lifecycle.*
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.repositories.Repository
import com.mnemo.samantha.domain.usecases.GetMasterAvatarPathUseCase
import com.mnemo.samantha.domain.usecases.GetMasterUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var getMasterUseCase : GetMasterUseCase

    @Inject
    lateinit var getMasterAvatarPathUseCase: GetMasterAvatarPathUseCase

    private var _master = MutableLiveData<Master>()
    val master : LiveData<Master>
    get() = _master

    val services: LiveData<List<Service>>

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getMasterUseCase.invoke().collect { _master.value = it }
        }


        services = repository.services
    }

    fun getMasterAvatarPath(masterId: Long) = getMasterAvatarPathUseCase.invoke(masterId)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}