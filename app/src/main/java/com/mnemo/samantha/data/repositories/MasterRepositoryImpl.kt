package com.mnemo.samantha.data.repositories

import com.mnemo.samantha.data.database.dao.MasterDAO
import com.mnemo.samantha.data.database.entities.asDatabaseModel
import com.mnemo.samantha.data.database.entities.asDomainModel
import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.repositories.MasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MasterRepositoryImpl @Inject constructor(val cache: MasterDAO) : MasterRepository {


    companion object{

        @Volatile
        private lateinit var INSTANCE: MasterRepositoryImpl

        fun getInstance(cache: MasterDAO) : MasterRepositoryImpl {

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = MasterRepositoryImpl(cache)
                }

                return INSTANCE
            }
        }
    }


    override fun getMaster() : Flow<Master> = cache.get().map { it.asDomainModel() }

    override suspend fun checkProfile() : Boolean{
        var shouldCreateProfile = false
        withContext(Dispatchers.IO){
            shouldCreateProfile = cache.getCount() < 1
        }
        return shouldCreateProfile
    }

    override suspend fun createProfile(master: Master){
        withContext(Dispatchers.IO){
            cache.insert(master.asDatabaseModel())
        }
    }

    override suspend fun updateProfileInfo(id: Long, name: String, profession: String, phoneNumber: String){
        withContext(Dispatchers.IO){
            cache.updateMasterInfo(id, name, profession, phoneNumber)
        }
    }


    override suspend fun updateProfileRegionInfo(id: Long, country: String, city: String, currency: String){
        withContext(Dispatchers.IO){
            cache.updateRegionInfo(id, country, city, currency)
        }
    }

    override fun getCurrency() = cache.getCurrency()
}