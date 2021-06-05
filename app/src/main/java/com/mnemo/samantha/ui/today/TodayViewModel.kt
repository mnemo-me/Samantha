package com.mnemo.samantha.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.repositories.Repository
import java.io.File
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

    val dateText = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(calendar.time)

    val appointments : LiveData<List<Appointment>>

    val storagePath: File

    init {
        DaggerAppComponent.create().inject(this)

        appointments = repository.getTodayClients(date, month, year)

        storagePath = repository.getStoragePath()!!
    }

}