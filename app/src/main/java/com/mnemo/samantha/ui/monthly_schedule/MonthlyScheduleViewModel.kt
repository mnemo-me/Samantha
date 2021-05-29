package com.mnemo.samantha.ui.monthly_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class MonthlyScheduleViewModel : ViewModel() {

    private var _month : Int
    val month : String

    private var _minDate: Long
    val minDate: Long
    get() {
        return _minDate
    }

    private var _maxDate: Long
    val maxDate: Long
    get() {
        return _maxDate
    }


    private val _days = MutableLiveData<MutableList<String>>()
    val days : LiveData<MutableList<String>>
    get() = _days

    init {

        val calendar = Calendar.getInstance()

        _month = calendar.get(Calendar.MONTH)
        month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)

        val year = calendar.get(Calendar.YEAR)
        val date = calendar.get(Calendar.DATE)
        _minDate = Date("$year/${_month+1}/$date").time
        _maxDate = Date("$year/${_month+2}/1").time


    }
}