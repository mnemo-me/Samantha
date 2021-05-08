package com.mnemo.samantha.ui.current_day

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import java.util.*

class TodayViewModel(val repository: Repository) : ViewModel() {

    private val calendar = Calendar.getInstance()

    val date = calendar.get(Calendar.DATE)

    private val _month = calendar.get(Calendar.MONTH)
    val month  = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

    private val year = calendar.get(Calendar.YEAR)

    val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

    var todayClients = repository.getTodayClients(date, _month, year)

}