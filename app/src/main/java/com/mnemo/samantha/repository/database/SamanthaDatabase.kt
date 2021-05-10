package com.mnemo.samantha.repository.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mnemo.samantha.repository.data.Statistics
import com.mnemo.samantha.repository.database.dao.AppointmentDAO
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.dao.MasterDAO
import com.mnemo.samantha.repository.database.dao.ServiceDAO
import com.mnemo.samantha.repository.database.entity.*

@Database(entities = [Master::class, Client::class, Appointment::class, Service::class], version = 1, exportSchema = false)
abstract class SamanthaDatabase : RoomDatabase() {

    abstract val masterDAO: MasterDAO
    abstract val clientDao: ClientDAO
    abstract val appointmentDAO: AppointmentDAO
    abstract val serviceDAO: ServiceDAO

    companion object{

        @Volatile
        private var INSTANCE: SamanthaDatabase? = null

        fun getInstance(context: Context): SamanthaDatabase{

            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, SamanthaDatabase::class.java, "samantha_database")
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }


    // Profile
    fun checkProfile() = masterDAO.get()

    fun getCurrency() = masterDAO.getCurrency()


    // Clients
    fun getClient(clientId: Long) = clientDao.get(clientId)

    fun updateClientInfo(client: Client) = clientDao.update(client)

    fun addClient(client: Client) =  clientDao.insert(client)

    fun removeClient(clientId: Long) = clientDao.remove(clientId)

    fun getClientList() = clientDao.getAll()


    // Schedule
    fun getDaySchedule(date: Int, month: Int, year: Int) = appointmentDAO.getDaySchedule(date, month, year)

    fun getTodayClients(date: Int, month: Int, year: Int) = appointmentDAO.getTodayClients(date, month, year, APPOINTMENT_STATE_BUSY)

    fun addAppointment(appointment: Appointment) = appointmentDAO.insert(appointment)

    fun updateAppointmentState(appointmentId: Long, appointmentState: Int) = appointmentDAO.updateAppointmentState(appointmentId, appointmentState)

    fun bookClient(appointmentId: Long, clientId: Long, serviceCost: Int?){
        val client = clientDao.getClient(clientId)
        appointmentDAO.bookClient(appointmentId, client.id, client.name, client.phoneNumber, serviceCost, APPOINTMENT_STATE_BUSY)
    }

    fun bookNewClient(appointmentId: Long, serviceCost: Int?){
        val client = clientDao.getLastAddedClient()
        appointmentDAO.bookClient(appointmentId, client.id, client.name, client.phoneNumber, serviceCost, APPOINTMENT_STATE_BUSY)
    }


    // Statistics
    fun getWorkingYears() = appointmentDAO.getWorkingYears()

    fun getWorkingMonths(year: Int) = appointmentDAO.getWorkingMonths(year)

    fun getWorkingDaysCount(month: Int, year: Int) = appointmentDAO.getWorkingDaysCount(month, year)

    fun getClientsCount(month: Int, year: Int) = appointmentDAO.getClientsCount(month, year)

    fun getMonthRevenue(month: Int, year: Int) = appointmentDAO.getMonthRevenue(month, year)


    // Services
    fun getServiceList() = serviceDAO.getAll()

    fun getService(serviceId: Long) = serviceDAO.get(serviceId)

    fun addService(service: Service) = serviceDAO.insert(service)

    fun updateService(service: Service) = serviceDAO.update(service)

}