package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class GetTodayClientsUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend operator fun invoke(date: Int, month: Int, year: Int) = appointmentsRepository.getTodayClients(date, month, year)
}