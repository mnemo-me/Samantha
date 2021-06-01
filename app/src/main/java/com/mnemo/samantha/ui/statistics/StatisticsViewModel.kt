package com.mnemo.samantha.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.domain.Statistics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class StatisticsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val statistics = MutableLiveData<List<Statistics>>()
    val annualRevenue : LiveData<Long>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        val calendar = Calendar.getInstance()
        annualRevenue = repository.getAnnualRevenue(calendar.get(Calendar.YEAR))

        viewModelScope.launch {
            statistics.value = repository.getStatistics()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}