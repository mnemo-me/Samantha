package com.mnemo.samantha.domain.repositories

import com.mnemo.samantha.domain.entities.Master
import kotlinx.coroutines.flow.Flow


interface MasterRepository {

    suspend fun getMaster() : Flow<Master>
    suspend fun checkProfile() : Boolean
    suspend fun createProfile(master: Master)
    suspend fun updateProfileInfo(id: Long, name: String, profession: String, phoneNumber: String)
    suspend fun updateProfileRegionInfo(id: Long, country: String, city: String, currency: String)
    suspend fun getCurrency() : Flow<String>
}