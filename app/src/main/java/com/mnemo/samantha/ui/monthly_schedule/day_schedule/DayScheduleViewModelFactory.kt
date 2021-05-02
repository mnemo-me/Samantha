package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.database.dao.AppointmentDAO

class DayScheduleViewModelFactory(val dataSource: AppointmentDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DayScheduleViewModel::class.java)){
            return DayScheduleViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}