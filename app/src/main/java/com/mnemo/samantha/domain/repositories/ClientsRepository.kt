package com.mnemo.samantha.domain.repositories

import com.mnemo.samantha.domain.entities.Client
import kotlinx.coroutines.flow.Flow


interface ClientsRepository {

    suspend fun getClients() : Flow<List<Client>>
    suspend fun getClient(clientId: Long) : Flow<Client>
    suspend fun getNewClientId() : Long
    suspend fun addClient(client: Client)
    suspend fun removeClient(clientId: Long)
}