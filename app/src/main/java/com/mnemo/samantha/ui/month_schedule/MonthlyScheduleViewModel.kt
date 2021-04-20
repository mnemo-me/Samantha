package com.mnemo.samantha.ui.month_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MonthlyScheduleViewModel : ViewModel() {

    private val _days = MutableLiveData<MutableList<String>>()
    val days : LiveData<MutableList<String>>
    get() = _days

    init {
        _days.value = mutableListOf()
        for (i in 1..31){
            _days.value!!.add("$i")
        }
    }
}