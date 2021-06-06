package com.mnemo.samantha.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Statistics
import com.mnemo.samantha.domain.usecases.GetAnnualRevenueUseCase
import com.mnemo.samantha.domain.usecases.GetStatisticsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class StatisticsViewModel : ViewModel() {

    @Inject
    lateinit var getStatisticsUseCase: GetStatisticsUseCase

    @Inject
    lateinit var getAnnualRevenueUseCase: GetAnnualRevenueUseCase

    private val _statistics = MutableLiveData<List<Statistics>>()
    val statistics : LiveData<List<Statistics>>
    get() = _statistics

    private val _annualRevenue = MutableLiveData<Long>()
    val annualRevenue : LiveData<Long>
    get() = _annualRevenue

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            getAnnualRevenueUseCase.invoke(calendar.get(Calendar.YEAR)).collect { _annualRevenue.value = it }
            _statistics.value = getStatisticsUseCase.invoke()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}