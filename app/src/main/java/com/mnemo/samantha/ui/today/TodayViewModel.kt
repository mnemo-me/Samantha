package com.mnemo.samantha.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.repositories.Repository
import com.mnemo.samantha.domain.usecases.GetTodayClientsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TodayViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var getTodayClientsUseCase: GetTodayClientsUseCase

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments : LiveData<List<Appointment>>
    get() = _appointments

    val storagePath: File

    private val calendar = Calendar.getInstance()

    private val date = calendar.get(Calendar.DATE)
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)

    val dateText = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(calendar.time)

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getTodayClientsUseCase.invoke(date, month, year).collect { _appointments.value = it }
        }

        storagePath = repository.getStoragePath()!!
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}