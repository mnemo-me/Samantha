package com.mnemo.samantha.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mnemo.samantha.repository.database.dao.AppointmentDAO
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.APPOINTMENT_STATE_BUSY
import com.mnemo.samantha.repository.database.entity.Appointment
import com.mnemo.samantha.repository.database.entity.Client

@Database(entities = [Client::class, Appointment::class], version = 1, exportSchema = false)
abstract class SamanthaDatabase : RoomDatabase() {

    abstract val clientDao: ClientDAO
    abstract val appointmentDAO: AppointmentDAO

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
}