package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class GetDayScheduleUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend fun invoke(date: Int, month: Int, year: Int) = appointmentsRepository.getDaySchedule(date, month, year)
}