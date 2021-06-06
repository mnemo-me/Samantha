package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.repositories.ServicesRepository
import javax.inject.Inject

class AddServiceUseCase @Inject constructor(val servicesRepository: ServicesRepository) {

    suspend fun invoke(service: Service) = servicesRepository.addService(service)
}