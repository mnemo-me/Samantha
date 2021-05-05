package com.mnemo.samantha.ui.current_day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class TodayViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodayViewModel::class.java)){
            return TodayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}