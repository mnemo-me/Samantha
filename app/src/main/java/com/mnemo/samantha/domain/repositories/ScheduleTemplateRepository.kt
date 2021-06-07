package com.mnemo.samantha.domain.repositories

import com.mnemo.samantha.domain.entities.ScheduleTemplate
import kotlinx.coroutines.flow.Flow


interface ScheduleTemplateRepository {

    suspend fun getSchedule() : Flow<ScheduleTemplate>
    suspend fun addSchedule(scheduleTemplate: ScheduleTemplate)
}