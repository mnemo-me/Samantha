package com.mnemo.samantha.domain

import com.mnemo.samantha.domain.entities.APPOINTMENT_STATE_FREE
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.entities.ScheduleTemplate
import com.mnemo.samantha.domain.repositories.Repository
import java.util.*

class ApplyScheduleUseCase(val repository: Repository) {

    suspend fun invoke(scheduleTemplate: ScheduleTemplate, days: Int, month: Int, year: Int){

        val schedule = mutableListOf<Appointment>()

        for (i in 1..days) {

            val calendar = Calendar.getInstance()
            calendar.set(year, month, i)

            if (scheduleTemplate.workingDays.contains(calendar.get(Calendar.DAY_OF_WEEK))) {

                for (y in scheduleTemplate.workingTimeStart..scheduleTemplate.workingTimeEnd step scheduleTemplate.timeSector) {
                    if (scheduleTemplate.haveBreak) {
                        if (!(y >= scheduleTemplate.breakTimeStart!! && y <= scheduleTemplate.breakTimeEnd!!)) {
                            schedule.add(
                                Appointment(
                                    time = y,
                                    date = i,
                                    month = month,
                                    year = year,
                                    client = null,
                                    services = null,
                                    serviceCost = null,
                                    timeToComplete = null,
                                    state = APPOINTMENT_STATE_FREE
                                )
                            )
                        }

                    } else {
                        schedule.add(
                            Appointment(
                                time = y,
                                date = i,
                                month = month,
                                year = year,
                                client = null,
                                services = null,
                                serviceCost = null,
                                timeToComplete = null,
                                state = APPOINTMENT_STATE_FREE
                            )
                        )
                    }
                }
            }
        }

        //repository.addAppointments()
    }
}