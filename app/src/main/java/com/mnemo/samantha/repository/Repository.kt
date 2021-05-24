package com.mnemo.samantha.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.*
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.repository.database.entity.*
import com.mnemo.samantha.repository.file_storage.FileStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class Repository {

    @Inject
    lateinit var database : SamanthaDatabase

    @Inject
    lateinit var fileStorage: FileStorage

    init {
        DaggerAppComponent.create().inject(this)
    }

    companion object{

        @Volatile
        private lateinit var INSTANCE: Repository

        fun getInstance() : Repository{

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = Repository()
                }

                return INSTANCE
            }
        }
    }

    val master: LiveData<Master> = Transformations.map(database.masterDAO.get()){
        it.asDomainModel()
    }

    val clients: LiveData<List<Client>> = Transformations.map(database.clientDao.getAll()){
        it.asDomainModel()
    }

    val services: LiveData<List<Service>> = Transformations.map(database.serviceDAO.getAll()){
        it.asDomainModel()
    }



    // Profile
    suspend fun checkProfile() : Boolean{
        var shouldCreateProfile = false
        withContext(Dispatchers.IO){
            shouldCreateProfile = database.masterDAO.getCount() < 1
        }
        return shouldCreateProfile
    }

    suspend fun createProfile(master: Master){
        withContext(Dispatchers.IO){
            database.masterDAO.insert(master.asDatabaseModel())
        }
    }

    fun getCurrency() = database.masterDAO.getCurrency()


    // Clients
    fun getClient(clientId: Long) = Transformations.map(database.clientDao.get(clientId)){
        it.asDomainModel()
    }

    suspend fun addClient(client: Client){
        withContext(Dispatchers.IO) {
            database.clientDao.insert(client.asDatabaseModel())
        }
    }

    suspend fun removeClient(clientId: Long) {
        withContext(Dispatchers.IO){
            database.clientDao.removeClient(clientId)
        }
    }

    suspend fun saveClientAvatar(bitmap: Bitmap, clientId: Long){
        withContext(Dispatchers.IO){
            fileStorage.saveClientAvatar(bitmap, clientId)
        }
    }

    fun getClientAvatarPath(clientId: Long) = fileStorage.getClientAvatarPath(clientId)


    // Appointments
    fun getDaySchedule(date: Int, month: Int, year: Int) = Transformations.map(database.appointmentDAO.getDaySchedule(date, month, year)){
        it.asDomainModel()
    }

    fun getTodayClients(date: Int, month: Int, year: Int) = Transformations.map(database.appointmentDAO.getTodayClients(date, month, year, APPOINTMENT_STATE_BUSY)){
        it.asDomainModel()
    }

    private suspend fun addAppointment(databaseAppointment: DatabaseAppointment){
        database.appointmentDAO.insert(databaseAppointment)
    }

    suspend fun bookClient(appointmentId: Long, client: Client, serviceCost: Int){
        withContext(Dispatchers.IO){
            val databaseClient = client.asDatabaseModel()
            database.appointmentDAO.bookClient(appointmentId, databaseClient.id, databaseClient.name, databaseClient.phoneNumber , serviceCost, APPOINTMENT_STATE_BUSY)
        }
    }

    suspend fun bookNewClient(appointmentId: Long, client: Client, serviceCost: Int){
        withContext(Dispatchers.IO) {
            addClient(client)
            bookClient(appointmentId, client, serviceCost)
        }
    }

    suspend fun updateAppointmentState(appointmentId: Long, appointmentState: Int){
        withContext(Dispatchers.IO){
            database.appointmentDAO.updateAppointmentState(appointmentId, appointmentState)
        }
    }


    // Statistics
    suspend fun getStatistics() : List<Statistics> {

        val statistics = mutableListOf<Statistics>()

        withContext(Dispatchers.IO) {
            val workingYears = database.appointmentDAO.getWorkingYears()

            workingYears.forEach { year ->
                val workingMonths = database.appointmentDAO.getWorkingMonths(year)

                workingMonths.forEach { month ->

                    val workingDaysCount = database.appointmentDAO.getWorkingDaysCount(month, year)
                    val clientsCount = database.appointmentDAO.getClientsCount(month, year)
                    val revenue = database.appointmentDAO.getMonthRevenue(month, year)

                    statistics.add(Statistics(month, year, workingDaysCount, clientsCount, revenue))
                }
            }
        }

        return statistics
    }



    // Services
    fun getService(serviceId: Long) = database.serviceDAO.get(serviceId)

    suspend fun addService(service: Service) {
        withContext(Dispatchers.IO){
            database.serviceDAO.insert(service.asDatabaseModel())
        }
    }


    // Schedule
    fun getSchedule() = Transformations.map(database.scheduleTemplateDAO.get()){
        it.asDomainModel()
    }

    suspend fun addSchedule(scheduleTemplate: ScheduleTemplate){
        withContext(Dispatchers.IO){
            database.scheduleTemplateDAO.insert(scheduleTemplate.asDatabaseModel())
        }
    }

    suspend fun applyScheduleTemplate(scheduleTemplate: ScheduleTemplate, days: Int, month: Int, year: Int){
        withContext(Dispatchers.IO) {
            for (i in 1..days) {
                for (y in scheduleTemplate.workingTimeStart..scheduleTemplate.workingTimeEnd step scheduleTemplate.timeSector) {
                    addAppointment(
                        DatabaseAppointment(
                            time = y,
                            date = i,
                            month = month,
                            year = year,
                            client = null,
                            serviceCost = null,
                            state = APPOINTMENT_STATE_FREE
                        )
                    )
                }
            }
        }
    }


    // File storage
    fun getStoragePath() = fileStorage.storageDir

}