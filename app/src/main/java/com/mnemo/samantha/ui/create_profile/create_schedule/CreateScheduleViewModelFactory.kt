package com.mnemo.samantha.ui.create_profile.create_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class CreateScheduleViewModelFactory(val scheduleId: Long, val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateScheduleViewModel::class.java)){
            return CreateScheduleViewModel(scheduleId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}