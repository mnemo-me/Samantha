package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.dao.AppointmentDAO

class DayScheduleViewModelFactory(val year: Int, val month: Int, val date: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DayScheduleViewModel::class.java)){
            return DayScheduleViewModel(year, month, date) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}