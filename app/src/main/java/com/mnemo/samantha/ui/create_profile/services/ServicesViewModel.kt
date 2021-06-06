package com.mnemo.samantha.ui.create_profile.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.usecases.GetCurrencyUseCase
import com.mnemo.samantha.domain.usecases.GetServicesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServicesViewModel : ViewModel() {

    @Inject
    lateinit var getServicesUseCase: GetServicesUseCase

    @Inject
    lateinit var getCurrencyUseCase : GetCurrencyUseCase

    private val _services = MutableLiveData<List<Service>>()
    val services : LiveData<List<Service>>
    get() = _services

    private var _currency = MutableLiveData<String>()
    val currency : LiveData<String>
    get() = _currency

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getServicesUseCase.invoke().collect { _services.value = it }
            getCurrencyUseCase.invoke().collect { _currency.value = it }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}