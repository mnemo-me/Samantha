package com.mnemo.samantha.ui.monthly_schedule

import androidx.lifecycle.ViewModel
import java.util.*

class MonthlyScheduleViewModel : ViewModel() {


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


    init {
        _minDate = getCalendarMinDate()
        _maxDate = getCalendarMaxDate()
    }


    private fun getCalendarMinDate() : Long{

        val calendar = Calendar.getInstance()

        return calendar.timeInMillis
    }


    private fun getCalendarMaxDate() : Long{

        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)

        val nextMonth = if (currentMonth == 11) 0 else currentMonth + 1
        val nextMonthYear = if (nextMonth == 0) currentYear + 1 else currentYear

        calendar.set(nextMonthYear, nextMonth, 1)

        val nextMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        calendar.set(nextMonthYear, nextMonth, nextMonthMaxDate)

        return calendar.timeInMillis
    }
}