package com.mnemo.samantha.data.repositories

import com.mnemo.samantha.data.database.dao.ClientDAO
import com.mnemo.samantha.data.database.entities.asDatabaseModel
import com.mnemo.samantha.data.database.entities.asDomainModel
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.repositories.ClientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClientsRepositoryImpl @Inject constructor(val cache : ClientDAO) : ClientsRepository {


    companion object{

        @Volatile
        private lateinit var INSTANCE: ClientsRepositoryImpl

        fun getInstance(cache: ClientDAO) : ClientsRepositoryImpl {

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = ClientsRepositoryImpl(cache)
                }

                return INSTANCE
            }
        }
    }



    override suspend fun getClients() = cache.getAll().map { it.asDomainModel() }

    override suspend fun getClient(clientId: Long) = cache.get(clientId).map { it.asDomainModel() }

    override suspend fun addClient(client: Client){
        withContext(Dispatchers.IO) {
            cache.insert(client.asDatabaseModel())
        }
    }

    override suspend fun getNewClientId(): Long = cache.getNewClientId()

    override suspend fun removeClient(clientId: Long) {
        withContext(Dispatchers.IO){
            cache.removeClient(clientId)
        }
    }

}