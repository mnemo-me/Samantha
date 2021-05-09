package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DayScheduleViewModel(val year: Int, val month: Int, val date: Int, val repository: Repository) : ViewModel() {

    private val calendar = Calendar.getInstance()

    val dateText : String


    private var _appointments = repository.getDaySchedule(date, month, year)
    val appointments : LiveData<List<Appointment>>
    get() = _appointments


    init {
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