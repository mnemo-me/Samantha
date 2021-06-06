package com.mnemo.samantha.data

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.*
import com.mnemo.samantha.data.database.SamanthaDatabase
import com.mnemo.samantha.data.database.entities.*
import com.mnemo.samantha.data.file_storage.FileStorage
import com.mnemo.samantha.domain.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
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

    override val clients: LiveData<List<Client>> = Transformations.map(database.clientDao.getAll()){
        it.asDomainModel()
    }

    override val services: LiveData<List<Service>> = Transformations.map(database.serviceDAO.getAll()){
        it.asDomainModel()
    }



    // Clients
    override fun getClient(clientId: Long) = Transformations.map(database.clientDao.get(clientId)){
        it.asDomainModel()
    }

    override suspend fun addClient(client: Client, clientAvatar: Bitmap?){
        withContext(Dispatchers.IO) {
            database.clientDao.insert(client.asDatabaseModel())
            val clientId = if (client.id == 0L) database.clientDao.getNewClient().id else client.id
            if (clientAvatar != null) saveClientAvatar(clientAvatar, clientId)
        }
    }

    override suspend fun removeClient(clientId: Long) {
        withContext(Dispatchers.IO){
            database.clientDao.removeClient(clientId)
        }
    }

    override suspend fun saveClientAvatar(bitmap: Bitmap, clientId: Long){
        withContext(Dispatchers.IO){
            fileStorage.saveClientAvatar(bitmap, clientId)
        }
    }

    override fun getClientAvatarPath(clientId: Long) = fileStorage.getClientAvatarPath(clientId)


    // Appointments
    override fun getDaySchedule(date: Int, month: Int, year: Int) = Transformations.map(database.appointmentDAO.getDaySchedule(date, month, year)){
        it.asDomainModel()
    }

    override fun getTodayClients(date: Int, month: Int, year: Int) = Transformations.map(database.appointmentDAO.getTodayClients(date, month, year, APPOINTMENT_STATE_BUSY)){
        it.asDomainModel()
    }

    override suspend fun addAppointment(appointment: Appointment){
        database.appointmentDAO.insert(appointment.asDatabaseModel())
    }

    override suspend fun bookClient(appointmentId: Long, clientId: Long, services: List<Service>, serviceCost: Long, serviceTimeToComplete: Int){
        withContext(Dispatchers.IO){
            val client = if (clientId != 0L) database.clientDao.getClient(clientId) else database.clientDao.getNewClient()
            database.appointmentDAO.bookClient(appointmentId, client.id, client.name, client.phoneNumber, services.asDatabaseModel(), serviceCost, serviceTimeToComplete, APPOINTMENT_STATE_BUSY)
        }
    }

    override fun getClientAppointments(clientId: Long) = Transformations.map(database.appointmentDAO.getClientAppointments(clientId)){
        it.asDomainModel()
    }

    override suspend fun updateAppointmentState(appointmentId: Long, appointmentState: Int){
        withContext(Dispatchers.IO){
            database.appointmentDAO.updateAppointmentState(appointmentId, appointmentState)
        }
    }


    // Statistics
    override suspend fun getStatistics() : List<Statistics> {

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

    override fun getAnnualRevenue(year: Int) = database.appointmentDAO.getAnnualRevenue(year)

    // Services
    override fun getService(serviceId: Long) = Transformations.map(database.serviceDAO.get(serviceId)){
        it.asDomainModel()
    }

    override suspend fun addService(service: Service) {
        withContext(Dispatchers.IO){
            database.serviceDAO.insert(service.asDatabaseModel())
        }
    }


    // Schedule
    override fun getSchedule() = Transformations.map(database.scheduleTemplateDAO.get()){
        it.asDomainModel()
    }

    override suspend fun addSchedule(scheduleTemplate: ScheduleTemplate){
        withContext(Dispatchers.IO){
            database.scheduleTemplateDAO.insert(scheduleTemplate.asDatabaseModel())
        }
    }

    override suspend fun applyScheduleTemplate(scheduleTemplate: ScheduleTemplate, days: Int, month: Int, year: Int){
        withContext(Dispatchers.IO) {
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
        }
    }


    // File storage
    override fun getStoragePath() = fileStorage.storageDir

}