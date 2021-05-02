package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.database.dao.AppointmentDAO
import com.mnemo.samantha.repository.database.entity.Appointment

class DayScheduleViewModel(val database: AppointmentDAO) : ViewModel() {

    private var _appointments = MutableLiveData<MutableList<Appointment>>()
    val appointments : LiveData<MutableList<Appointment>>
    get() = _appointments


    init {
        _appointments.value = mutableListOf<Appointment>()

        for (i in 0..24){
            _appointments.value!!.add(Appointment(appointmentTime = 123, appointmentDay = 123, appointmentMonth = 123, appointmentClient = 123, appointmentYear = 123, appointmentSummaryCost = 123))
        }
    }
}