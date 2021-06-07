package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import com.mnemo.samantha.domain.repositories.ClientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookClientUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository, val clientsRepository: ClientsRepository, val getClientUseCase: GetClientUseCase) {

    suspend operator fun invoke(appointmentId: Long, clientId: Long, services: List<Service>, serviceCost: Long, serviceTimeToComplete: Int){
        withContext(Dispatchers.IO){
            val client = if (clientId != 0L) getClientUseCase(clientId).first() else getClientUseCase(clientsRepository.getNewClientId()).first()
            appointmentsRepository.bookClient(appointmentId, client, services, serviceCost, serviceTimeToComplete)
        }
    }
}