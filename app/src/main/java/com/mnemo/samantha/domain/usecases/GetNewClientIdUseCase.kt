package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.ClientsRepository
import javax.inject.Inject

class GetNewClientIdUseCase @Inject constructor(val clientsRepository: ClientsRepository) {

    suspend fun invoke() = clientsRepository.getNewClientId()
}