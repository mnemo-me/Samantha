package com.mnemo.samantha.ui.create_profile.create_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateScheduleViewModelFactory(val scheduleId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateScheduleViewModel::class.java)){
            return CreateScheduleViewModel(scheduleId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}