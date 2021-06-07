package com.mnemo.samantha.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.usecases.GetTodayClientsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TodayViewModel : ViewModel() {

    @Inject
    lateinit var getTodayClientsUseCase: GetTodayClientsUseCase

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments : LiveData<List<Appointment>>
    get() = _appointments

    private val calendar = Calendar.getInstance()

    private val date = calendar.get(Calendar.DATE)
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)

    val dateText = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(calendar.time)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getTodayClientsUseCase(date, month, year).collect { _appointments.value = it }
        }
    }

}