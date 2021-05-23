package com.mnemo.samantha.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Appointment
import com.mnemo.samantha.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TodayViewModel() : ViewModel() {

    @Inject
    lateinit var repository: Repository

    private val calendar = Calendar.getInstance()

    private val date = calendar.get(Calendar.DATE)
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)

    val dateText = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(calendar.time)

    val appointments : LiveData<List<Appointment>>

    init {
        DaggerAppComponent.create().inject(this)

        appointments = repository.getTodayClients(date, month, year)
    }

}