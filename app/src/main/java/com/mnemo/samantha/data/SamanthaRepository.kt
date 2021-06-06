package com.mnemo.samantha.data


import androidx.lifecycle.Transformations
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.*
import com.mnemo.samantha.data.database.SamanthaDatabase
import com.mnemo.samantha.data.database.entities.*
import com.mnemo.samantha.data.file_storage.FileStorage
import com.mnemo.samantha.domain.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SamanthaRepository : Repository{

    @Inject
    lateinit var database : SamanthaDatabase

    @Inject
    lateinit var fileStorage: FileStorage

    init {
        DaggerAppComponent.create().inject(this)
    }

    companion object{

        @Volatile
        private lateinit var INSTANCE: SamanthaRepository

        fun getInstance() : SamanthaRepository{

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = SamanthaRepository()
                }

                return INSTANCE
            }
        }
    }










    override fun getSchedule() = Transformations.map(database.scheduleTemplateDAO.get()){
        it.asDomainModel()
    }

    override suspend fun addSchedule(scheduleTemplate: ScheduleTemplate){
        withContext(Dispatchers.IO){
            database.scheduleTemplateDAO.insert(scheduleTemplate.asDatabaseModel())
        }
    }

    override suspend fun applyScheduleTemplate(scheduleTemplate: ScheduleTemplate, days: Int, month: Int, year: Int){
        /*withContext(Dispatchers.IO) {
            for (i in 1..days) {

                val calendar = Calendar.getInstance()
                calendar.set(year, month, i)

                if (scheduleTemplate.workingDays.contains(calendar.get(Calendar.DAY_OF_WEEK))) {

                    for (y in scheduleTemplate.workingTimeStart..scheduleTemplate.workingTimeEnd step scheduleTemplate.timeSector) {
                        if (scheduleTemplate.haveBreak) {
                            if (!(y >= scheduleTemplate.breakTimeStart!! && y <= scheduleTemplate.breakTimeEnd!!)) {
                                addAppointment(
                                    Appointment(
                                        time = y,
                                        date = i,
                                        month = month,
                                        year = year,
                                        client = null,
                                        services = null,
                                        serviceCost = null,
                                        timeToComplete = null,
                                        state = APPOINTMENT_STATE_FREE
                                    )
                                )
                            }

                        } else {
                            addAppointment(
                                Appointment(
                                    time = y,
                                    date = i,
                                    month = month,
                                    year = year,
                                    client = null,
                                    services = null,
                                    serviceCost = null,
                                    timeToComplete = null,
                                    state = APPOINTMENT_STATE_FREE
                                )
                            )
                        }
                    }
                }
            }
        }*/
    }


    // File storage
    override fun getStoragePath() = fileStorage.storageDir

}