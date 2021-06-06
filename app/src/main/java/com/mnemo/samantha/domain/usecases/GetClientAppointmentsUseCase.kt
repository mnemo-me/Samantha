package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class GetClientAppointmentsUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend fun invoke(clientId: Long) = appointmentsRepository.getClientAppointments(clientId)
}