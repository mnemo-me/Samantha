package com.mnemo.samantha.ui.current_day

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Appointment
import java.util.*

class TodayViewModel(val repository: Repository) : ViewModel() {

    var todayClients : LiveData<List<Appointment>>

    init {

        val calendar = Calendar.getInstance()

        val date = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        todayClients = repository.getTodayClients(date, month, year)
    }
}