package com.mnemo.samantha.ui.current_day

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.database.entity.Client

class TodayViewModel : ViewModel() {

    private val _todaySchedule = MutableLiveData<LinkedHashMap<Int, Client?>>()
    val todaySchedule : LiveData<LinkedHashMap<Int, Client?>>
    get() = _todaySchedule

    init {
        _todaySchedule.value = linkedMapOf()
        _todaySchedule.value!![-1] = null

        for(i in 1..24){
            _todaySchedule.value!![13 + i] = Client(123,"Samantha $i", "")
        }
    }
}