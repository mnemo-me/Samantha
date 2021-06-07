package com.mnemo.samantha.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Statistics
import com.mnemo.samantha.domain.usecases.GetAnnualRevenueUseCase
import com.mnemo.samantha.domain.usecases.GetStatisticsUseCase
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

    private var _annualRevenue = MutableLiveData<Long>()
    val annualRevenue : LiveData<Long>
    get() = _annualRevenue

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            _annualRevenue.value = getAnnualRevenueUseCase(calendar.get(Calendar.YEAR))
            _statistics.value = getStatisticsUseCase()
        }
    }

}