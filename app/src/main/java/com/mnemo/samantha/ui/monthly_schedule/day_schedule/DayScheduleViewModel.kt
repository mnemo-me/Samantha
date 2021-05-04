package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.dao.AppointmentDAO
import com.mnemo.samantha.repository.database.entity.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DayScheduleViewModel(val year: Int, val month: Int, val date: Int, val repository: Repository) : ViewModel() {

    private var _appointments = repository.getDaySchedule(date, month, year)
    val appointments : LiveData<List<Appointment>>
    get() = _appointments


    init {

        val calendar = Calendar.getInstance()
        calendar.set(year, month, date)


        /*
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..1380 step 60) {
                database.insert(Appointment(appointmentId = calendar.timeInMillis + i * 60000, appointmentTime = i, appointmentDate = date, appointmentMonth = month, appointmentClient = null, appointmentYear = year, appointmentSummaryCost = null))
            }
        }*/


    }

    fun addClient(appointmentId: Long, clientId: Long?, serviceCost: Int?){
        CoroutineScope(Dispatchers.IO).launch {
            repository.bookClient(appointmentId, clientId, serviceCost)
        }
    }
}