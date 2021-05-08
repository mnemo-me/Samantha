package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.dao.AppointmentDAO
import com.mnemo.samantha.repository.database.entity.APPOINTMENT_STATE_FREE
import com.mnemo.samantha.repository.database.entity.Appointment
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DayScheduleViewModel(val year: Int, val month: Int, val date: Int, val repository: Repository) : ViewModel() {

    private val calendar = Calendar.getInstance()

    val monthText : String
    val dayOfWeek : String


    private var _appointments = repository.getDaySchedule(date, month, year)
    val appointments : LiveData<List<Appointment>>
    get() = _appointments


    init {
        calendar.set(year, month, date, 0, 0, 0)
        monthText = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())


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