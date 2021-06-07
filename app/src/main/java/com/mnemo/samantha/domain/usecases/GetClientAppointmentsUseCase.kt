package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class GetClientAppointmentsUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend operator fun invoke(clientId: Long) = appointmentsRepository.getClientAppointments(clientId)
}