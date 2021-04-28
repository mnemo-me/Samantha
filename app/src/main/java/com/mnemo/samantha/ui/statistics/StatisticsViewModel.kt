package com.mnemo.samantha.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.database.entity.MonthlyStatistics

class StatisticsViewModel : ViewModel() {

    private val _statistics = MutableLiveData<MutableList<StatisticsAdapter.DataItem>>()
    val statistics : LiveData<MutableList<StatisticsAdapter.DataItem>>
    get() = _statistics

    init {
        _statistics.value = mutableListOf()
        _statistics.value!!.add(StatisticsAdapter.DataItem.Header("240 000.00 р"))

        for (i in 1..12){
            _statistics.value!!.add(StatisticsAdapter.DataItem.MonthlyStatisticsItem(
                MonthlyStatistics(123, 2021, "March", 20, 48, 40000)
            ))
        }
    }
}