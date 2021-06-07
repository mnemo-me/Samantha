package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.ScheduleTemplate
import com.mnemo.samantha.domain.repositories.ScheduleTemplateRepository
import javax.inject.Inject

class AddScheduleUseCase @Inject constructor(val scheduleTemplateRepository: ScheduleTemplateRepository) {

    suspend operator fun invoke(scheduleTemplate: ScheduleTemplate) = scheduleTemplateRepository.addSchedule(scheduleTemplate)
}