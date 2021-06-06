package com.mnemo.samantha.data.repositories

import com.mnemo.samantha.data.database.dao.ServiceDAO
import com.mnemo.samantha.data.database.entities.asDatabaseModel
import com.mnemo.samantha.data.database.entities.asDomainModel
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.repositories.ServicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(val cache: ServiceDAO) : ServicesRepository {

    companion object{

        @Volatile
        private lateinit var INSTANCE: ServicesRepositoryImpl

        fun getInstance(cache: ServiceDAO) : ServicesRepositoryImpl {

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = ServicesRepositoryImpl(cache)
                }

                return INSTANCE
            }
        }
    }


    override suspend fun getServices() = cache.getAll().map { it.asDomainModel() }

    override suspend fun getService(serviceId: Long) = cache.get(serviceId).map { it.asDomainModel() }

    override suspend fun addService(service: Service) {
        withContext(Dispatchers.IO) {
            cache.insert(service.asDatabaseModel())
        }

    }
}