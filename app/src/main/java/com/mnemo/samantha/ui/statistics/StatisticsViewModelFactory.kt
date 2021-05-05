package com.mnemo.samantha.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemo.samantha.repository.Repository

class StatisticsViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)){
            return StatisticsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}