package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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


    init {
        DaggerAppComponent.create().inject(this)

        appointments = repository.getDaySchedule(date, month, year)

        calendar.set(year, month, date, 0, 0, 0)
        dateText = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(calendar.time)


/*
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..1380 step 60) {
                repository.addAppointment(Appointment(id = calendar.timeInMillis + i * 60000, time = i, date = date, month = month, client = Client(0L, "", ""), year = year, serviceCost = null, state = APPOINTMENT_STATE_FREE))
            }
        }

*/
    }

    fun updateAppointmentState(appointmentId: Long, appointmentState: Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateAppointmentState(appointmentId, appointmentState)
        }
    }
}