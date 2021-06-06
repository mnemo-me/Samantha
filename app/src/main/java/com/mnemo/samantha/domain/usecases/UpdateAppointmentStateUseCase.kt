package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class UpdateAppointmentStateUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend fun invoke(appointmentId: Long, appointmentState: Int) = appointmentsRepository.updateAppointmentState(appointmentId, appointmentState)
}