package com.mnemo.samantha.ui.create_profile.create_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.ScheduleTemplate
import com.mnemo.samantha.domain.usecases.AddScheduleUseCase
import com.mnemo.samantha.domain.usecases.ApplyScheduleUseCase
import com.mnemo.samantha.util.TimeTextConverter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateScheduleViewModel(private val scheduleId: Long) : ViewModel() {

    @Inject
    lateinit var addScheduleUseCase: AddScheduleUseCase

    @Inject
    lateinit var applyScheduleUseCase: ApplyScheduleUseCase

    val daysOfWeek : List<Int>

    init {
        DaggerAppComponent.create().inject(this)

        daysOfWeek = setupDaysOfWeek()
    }

    fun updateSchedule(workingTimeStartText: String, workingTimeEndText: String, haveBreak: Boolean, breakTimeStartText: String, breakTimeEndText: String, timeSectorText: String, workingDays: List<Int>){

        val workingTimeStart = TimeTextConverter.convertTextToMinutes(workingTimeStartText)
        val workingTimeEnd = TimeTextConverter.convertTextToMinutes(workingTimeEndText)
        val breakTimeStart = if (haveBreak) TimeTextConverter.convertTextToMinutes(breakTimeStartText) else null
        val breakTimeEnd = if (haveBreak) TimeTextConverter.convertTextToMinutes(breakTimeEndText) else null
        val timeSector = timeSectorText.toInt()

        val scheduleTemplate = ScheduleTemplate(scheduleId, workingTimeStart, workingTimeEnd, haveBreak, breakTimeStart, breakTimeEnd, timeSector, workingDays)

        viewModelScope.launch {

            addScheduleUseCase(scheduleTemplate)

            applySchedule(scheduleTemplate)
        }
    }

    private suspend fun applySchedule(scheduleTemplate: ScheduleTemplate){

        val calendar = Calendar.getInstance()

        val currentDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        applyScheduleUseCase(scheduleTemplate, currentDays, currentMonth, currentYear)

        val nextMonth = if (currentMonth == 11) 0 else currentMonth + 1
        val nextMonthYear = if (nextMonth == 0) currentYear + 1 else currentYear

        calendar.set(nextMonthYear, nextMonth, 1)

        val nextMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        applyScheduleUseCase(scheduleTemplate, nextMonthYear, nextMonth, nextMonthDays)
    }

    // Get time in String format
    fun getTime(hour: Int, minute: Int) : String{

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        return SimpleDateFormat("HH:mm").format(calendar.time)
    }

    private fun setupDaysOfWeek() : List<Int>{

        val daysOfWeek = mutableListOf<Int>()

        val calendar = Calendar.getInstance()
        val firstDayOfWeek = calendar.firstDayOfWeek

        if (firstDayOfWeek == 1) {
            daysOfWeek.add(Calendar.SUNDAY)
        }

        daysOfWeek.add(Calendar.MONDAY)
        daysOfWeek.add(Calendar.TUESDAY)
        daysOfWeek.add(Calendar.WEDNESDAY)
        daysOfWeek.add(Calendar.THURSDAY)
        daysOfWeek.add(Calendar.FRIDAY)
        daysOfWeek.add(Calendar.SATURDAY)

        if (firstDayOfWeek != 1) {
            daysOfWeek.add(Calendar.SUNDAY)
        }

        return daysOfWeek
    }

}