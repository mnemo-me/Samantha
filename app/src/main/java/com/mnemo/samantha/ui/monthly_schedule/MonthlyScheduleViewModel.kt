package com.mnemo.samantha.ui.monthly_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class MonthlyScheduleViewModel : ViewModel() {

    private var _month : Int
    val month : String


    private val _days = MutableLiveData<MutableList<String>>()
    val days : LiveData<MutableList<String>>
    get() = _days

    init {

        val calendar = Calendar.getInstance()

        _month = calendar.get(Calendar.MONTH)
        month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)



        /*
        _days.value = mutableListOf()
        for (i in 1..31){
            _days.value!!.add("$i")
        }*/
    }
}