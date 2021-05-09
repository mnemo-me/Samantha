package com.mnemo.samantha.ui.current_day

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import java.text.SimpleDateFormat
import java.util.*

class TodayViewModel(val repository: Repository) : ViewModel() {

    private val calendar = Calendar.getInstance()

    private val date = calendar.get(Calendar.DATE)
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)

    val dateText = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(calendar.time)

    var todayClients = repository.getTodayClients(date, month, year)

}