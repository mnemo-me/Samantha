package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Appointment
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DayScheduleViewModel(val year: Int, val month: Int, val date: Int) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val appointments : LiveData<List<Appointment>>

    private val calendar = Calendar.getInstance()

    val dateText : String

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        DaggerAppComponent.create().inject(this)

        appointments = repository.getDaySchedule(date, month, year)

        calendar.set(year, month, date, 0, 0, 0)
        dateText = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(calendar.time)

    }

    fun updateAppointmentState(appointmentId: Long, appointmentState: Int){
        viewModelScope.launch {
            repository.updateAppointmentState(appointmentId, appointmentState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}