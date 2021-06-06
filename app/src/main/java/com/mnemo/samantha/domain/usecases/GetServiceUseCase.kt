package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.ServicesRepository
import javax.inject.Inject

class GetServiceUseCase @Inject constructor(val servicesRepository: ServicesRepository) {

    suspend fun invoke(serviceId : Long) = servicesRepository.getService(serviceId)
}