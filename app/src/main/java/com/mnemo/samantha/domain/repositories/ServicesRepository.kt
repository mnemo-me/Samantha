package com.mnemo.samantha.domain.repositories

import com.mnemo.samantha.domain.entities.Service
import kotlinx.coroutines.flow.Flow

interface ServicesRepository {

    suspend fun getServices() : Flow<List<Service>>
    suspend fun getService(serviceId: Long) : Flow<Service>
    suspend fun addService(service: Service)
}