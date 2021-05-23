package com.mnemo.samantha.ui.create_profile.create_schedule

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.ScheduleTemplate
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.util.TimeTextConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateScheduleViewModel(val scheduleId: Long) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    // Coroutine
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun updateSchedule(workingTimeStartText: String, workingTimeEndText: String, breakTimeStartText: String, breakTimeEndText: String, timeSectorText: String){

        val workingTimeStart = TimeTextConverter.convertTextToMinutes(workingTimeStartText)
        val workingTimeEnd = TimeTextConverter.convertTextToMinutes(workingTimeEndText)
        val breakTimeStart = TimeTextConverter.convertTextToMinutes(breakTimeStartText)
        val breakTimeEnd = TimeTextConverter.convertTextToMinutes(breakTimeEndText)
        val timeSector = timeSectorText.toInt()

        val scheduleTemplate = ScheduleTemplate(scheduleId, workingTimeStart, workingTimeEnd, breakTimeStart, breakTimeEnd, timeSector)

        viewModelScope.launch {

            repository.addSchedule(scheduleTemplate)

            applySchedule(scheduleTemplate)
        }
    }

    private suspend fun applySchedule(scheduleTemplate: ScheduleTemplate){

        val calendar = Calendar.getInstance()

        val days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        repository.applyScheduleTemplate(scheduleTemplate, days, month, year)

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}