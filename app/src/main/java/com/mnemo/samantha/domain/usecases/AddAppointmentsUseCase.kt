package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class AddAppointmentsUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend fun invoke(appointments: List<Appointment>) = appointmentsRepository.addAppointments(appointments)
}