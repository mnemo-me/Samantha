package com.mnemo.samantha.data.repositories

import com.mnemo.samantha.data.database.dao.AppointmentDAO
import com.mnemo.samantha.data.database.entities.asDatabaseModel
import com.mnemo.samantha.data.database.entities.asDomainModel
import com.mnemo.samantha.domain.entities.*
import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppointmentsRepositoryImpl @Inject constructor(val cache: AppointmentDAO) : AppointmentsRepository{

    companion object{

        @Volatile
        private lateinit var INSTANCE: AppointmentsRepositoryImpl

        fun getInstance(cache: AppointmentDAO) : AppointmentsRepositoryImpl {

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = AppointmentsRepositoryImpl(cache)
                }

                return INSTANCE
            }
        }
    }

    override suspend fun getDaySchedule(date: Int, month: Int, year: Int) = cache.getDaySchedule(date, month, year).map { it.asDomainModel() }

    override suspend fun getTodayClients(date: Int, month: Int, year: Int) = cache.getTodayClients(date, month, year, APPOINTMENT_STATE_BUSY).map { it.asDomainModel() }

    override suspend fun addAppointments(appointments: List<Appointment>) = cache.insertAll(appointments.map { it.asDatabaseModel() })


    override suspend fun bookClient(appointmentId: Long, client: Client, services: List<Service>, serviceCost: Long, serviceTimeToComplete: Int){
        withContext(Dispatchers.IO){
            val databaseClient = client.asDatabaseModel()
            cache.bookClient(appointmentId, databaseClient.id, databaseClient.name, databaseClient.phoneNumber, services.asDatabaseModel(), serviceCost, serviceTimeToComplete, APPOINTMENT_STATE_BUSY)
        }
    }

    override suspend fun getClientAppointments(clientId: Long) = cache.getClientAppointments(clientId).map { it.asDomainModel() }

    override suspend fun updateAppointmentState(appointmentId: Long, appointmentState: Int){
        withContext(Dispatchers.IO){
            cache.updateAppointmentState(appointmentId, appointmentState)
        }
    }

    override suspend fun getStatistics() : List<Statistics> {

        val statistics = mutableListOf<Statistics>()

        withContext(Dispatchers.IO) {
            val workingYears = cache.getWorkingYears()

            workingYears.forEach { year ->
                val workingMonths = cache.getWorkingMonths(year)

                workingMonths.forEach { month ->

                    val workingDaysCount = cache.getWorkingDaysCount(month, year)
                    val clientsCount = cache.getClientsCount(month, year)
                    val revenue = cache.getMonthRevenue(month, year)

                    statistics.add(Statistics(month, year, workingDaysCount, clientsCount, revenue))
                }
            }
        }

        return statistics
    }

    override suspend fun getWorkingYears() = cache.getWorkingYears()

    override suspend fun getWorkingMonths(year: Int) = cache.getWorkingMonths(year)

    override suspend fun getWorkingDaysCount(month: Int, year: Int) = cache.getWorkingDaysCount(month, year)

    override suspend fun getClientsCount(month: Int, year: Int) = cache.getClientsCount(month, year)

    override suspend fun getMonthRevenue(month: Int, year: Int) = cache.getMonthRevenue(month, year)

    override suspend fun getAnnualRevenue(year: Int) = cache.getAnnualRevenue(year)
}