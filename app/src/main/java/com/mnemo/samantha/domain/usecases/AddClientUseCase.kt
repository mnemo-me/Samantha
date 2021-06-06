package com.mnemo.samantha.domain.usecases

import android.graphics.Bitmap
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.repositories.ClientsRepository
import javax.inject.Inject

class AddClientUseCase @Inject constructor(val clientsRepository: ClientsRepository, val saveClientAvatarUseCase: SaveClientAvatarUseCase) {

    suspend fun invoke(client: Client, clientAvatar: Bitmap?){
        clientsRepository.addClient(client)
        val clientId = if (client.id == 0L) clientsRepository.getNewClientId() else client.id
        if (clientAvatar != null) saveClientAvatarUseCase.invoke(clientAvatar, clientId)
    }
}