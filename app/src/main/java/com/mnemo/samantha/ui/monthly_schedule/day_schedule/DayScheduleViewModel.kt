package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.repositories.Repository
import com.mnemo.samantha.domain.usecases.GetDayScheduleUseCase
import com.mnemo.samantha.domain.usecases.UpdateAppointmentStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DayScheduleViewModel(val year: Int, val month: Int, val date: Int) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var getDayScheduleUseCase: GetDayScheduleUseCase

    @Inject
    lateinit var updateAppointmentStateUseCase: UpdateAppointmentStateUseCase

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments : LiveData<List<Appointment>>
    get() = _appointments

    val dateText : String

    val storagePath: File

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getDayScheduleUseCase.invoke(date, month, year).collect { _appointments.value = it }
        }

        val calendar = Calendar.getInstance()
        calendar.set(year, month, date, 0, 0, 0)
        dateText = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(calendar.time)

        storagePath = repository.getStoragePath()!!
    }

    fun updateAppointmentState(appointmentId: Long, appointmentState: Int){
        viewModelScope.launch {
            updateAppointmentStateUseCase.invoke(appointmentId, appointmentState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}