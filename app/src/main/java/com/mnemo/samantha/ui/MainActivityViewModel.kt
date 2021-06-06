package com.mnemo.samantha.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.usecases.CheckProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel : ViewModel(){

    @Inject
    lateinit var checkProfileUseCase: CheckProfileUseCase

    private var _shouldCreateProfile = MutableLiveData<Boolean>()
    val shouldCreateProfile : LiveData<Boolean>
    get() {
        return _shouldCreateProfile
    }

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            _shouldCreateProfile.value = checkProfileUseCase.invoke()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}