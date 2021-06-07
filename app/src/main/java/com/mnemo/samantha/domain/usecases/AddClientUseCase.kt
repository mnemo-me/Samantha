package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.repositories.ClientsRepository
import javax.inject.Inject

class AddClientUseCase @Inject constructor(val clientsRepository: ClientsRepository) {

    suspend operator fun invoke(client: Client) : Long = clientsRepository.addClient(client)
}