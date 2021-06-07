package com.mnemo.samantha.data.repositories

import com.mnemo.samantha.data.database.dao.ScheduleTemplateDAO
import com.mnemo.samantha.data.database.entities.asDatabaseModel
import com.mnemo.samantha.data.database.entities.asDomainModel
import com.mnemo.samantha.domain.entities.ScheduleTemplate
import com.mnemo.samantha.domain.repositories.ScheduleTemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleTemplateRepositoryImpl @Inject constructor(val cache: ScheduleTemplateDAO) : ScheduleTemplateRepository {

    companion object{

        @Volatile
        private lateinit var INSTANCE: ScheduleTemplateRepositoryImpl

        fun getInstance(cache: ScheduleTemplateDAO) : ScheduleTemplateRepositoryImpl {

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = ScheduleTemplateRepositoryImpl(cache)
                }

                return INSTANCE
            }
        }
    }

    override suspend fun getSchedule() = cache.get().map { it.asDomainModel() }

    override suspend fun addSchedule(scheduleTemplate: ScheduleTemplate){
        withContext(Dispatchers.IO){
            cache.insert(scheduleTemplate.asDatabaseModel())
        }
    }
}