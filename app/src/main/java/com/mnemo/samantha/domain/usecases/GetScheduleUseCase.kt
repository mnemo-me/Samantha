package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.ScheduleTemplateRepository
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(val scheduleTemplateRepository: ScheduleTemplateRepository) {

    suspend operator fun invoke() = scheduleTemplateRepository.getSchedule()
}