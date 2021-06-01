package com.mnemo.samantha.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.*
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.repository.database.entity.*
import com.mnemo.samantha.repository.file_storage.FileStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun updateProfileInfo(id: Long, name: String, profession: String, phoneNumber: String, masterAvatar: Bitmap?){
        withContext(Dispatchers.IO){
            database.masterDAO.updateMasterInfo(id, name, profession, phoneNumber)
            if (masterAvatar != null) saveMasterAvatar(masterAvatar, id)
        }
    }

    private suspend fun saveMasterAvatar(bitmap: Bitmap, masterId: Long){
        withContext(Dispatchers.IO){
            fileStorage.saveMasterAvatar(bitmap, masterId)
        }
    }

    fun getMasterAvatarPath(masterId: Long) = fileStorage.getMasterAvatarPath(masterId)

    suspend fun updateProfileRegionInfo(id: Long, country: String, city: String, currency: String){
        withContext(Dispatchers.IO){
            database.masterDAO.updateRegionInfo(id, country, city, currency)
        }
    }

    fun getCurrency() = database.masterDAO.getCurrency()


    // Clients
    fun getClient(clientId: Long) = Transformations.map(database.clientDao.get(clientId)){
        it.asDomainModel()
    }

    suspend fun addClient(client: Client, clientAvatar: Bitmap?){
        withContext(Dispatchers.IO) {
            database.clientDao.insert(client.asDatabaseModel())
            val clientId = if (client.id == 0L) database.clientDao.getNewClient().id else client.id
            if (clientAvatar != null) saveClientAvatar(clientAvatar, clientId)
        }
    }

    suspend fun removeClient(clientId: Long) {
        withContext(Dispatchers.IO){
            database.clientDao.removeClient(clientId)
        }
    }

    private suspend fun saveClientAvatar(bitmap: Bitmap, clientId: Long){
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

    suspend fun bookClient(appointmentId: Long, clientId: Long, services: List<Service>, serviceCost: Long, serviceTimeToComplete: Int){
        withContext(Dispatchers.IO){
            val client = if (clientId != 0L) database.clientDao.getClient(clientId) else database.clientDao.getNewClient()
            database.appointmentDAO.bookClient(appointmentId, client.id, client.name, client.phoneNumber, services.asDatabaseModel(), serviceCost, serviceTimeToComplete, APPOINTMENT_STATE_BUSY)
        }
    }

    fun getClientAppointments(clientId: Long) = Transformations.map(database.appointmentDAO.getClientAppointments(clientId)){
        it.asDomainModel()
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


    // File storage
    fun getStoragePath() = fileStorage.storageDir

}