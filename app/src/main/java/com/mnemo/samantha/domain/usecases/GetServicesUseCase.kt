package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.ServicesRepository
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(val servicesRepository: ServicesRepository) {

    suspend fun invoke() = servicesRepository.getServices()
}