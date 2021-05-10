package com.mnemo.samantha.repository

import android.content.Context
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.repository.database.entity.*

class Repository(val database: SamanthaDatabase) {

    companion object{

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(context: Context) : Repository{

            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Repository(SamanthaDatabase.getInstance(context))

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    // Profile
    fun checkProfile() = database.checkProfile()

    fun createProfile(master: Master) = database.createProfile(master)

    fun getCurrency() = database.getCurrency()

    fun getMaster() = database.getMaster()


    // Clients
    fun getClient(clientId: Long) = database.getClient(clientId)

    fun updateClientInfo(client: Client) = database.updateClientInfo(client)

    fun addClient(client: Client) =  database.addClient(client)

    fun removeClient(clientId: Long) = database.removeClient(clientId)

    fun getClientList() = database.getClientList()


    // Schedule
    fun getDaySchedule(date: Int, month: Int, year: Int) = database.getDaySchedule(date, month, year)

    fun getTodayClients(date: Int, month: Int, year: Int) = database.getTodayClients(date, month, year)

    fun addAppointment(appointment: Appointment) = database.addAppointment(appointment)

    fun updateAppointmentState(appointmentId: Long, appointmentState: Int) = database.updateAppointmentState(appointmentId, appointmentState)

    fun bookClient(appointmentId: Long, clientId: Long, serviceCost: Int?) = database.bookClient(appointmentId, clientId, serviceCost)

    fun bookNewClient(appointmentId: Long, serviceCost: Int?) = database.bookNewClient(appointmentId, serviceCost)


    // Statistics
    fun getWorkingYears() = database.getWorkingYears()

    fun getWorkingMonths(year: Int) = database.getWorkingMonths(year)

    fun getWorkingDaysCount(month: Int, year: Int) = database.getWorkingDaysCount(month, year)

    fun getClientsCount(month: Int, year: Int) = database.getClientsCount(month, year)

    fun getMonthRevenue(month: Int, year: Int) = database.getMonthRevenue(month, year)


    // Services
    fun getServiceList() = database.getServiceList()

    fun getService(serviceId: Long) = database.getService(serviceId)

    fun addService(service: Service) = database.addService(service)

    fun updateService(service: Service) = database.updateService(service)


    // Schedule
    fun getSchedule() = database.getSchedule()

    fun addSchedule(scheduleTemplate: ScheduleTemplate) = database.addSchedule(scheduleTemplate)

    fun updateSchedule(scheduleTemplate: ScheduleTemplate) = database.updateSchedule(scheduleTemplate)

    fun applyScheduleTemplate(scheduleTemplate: ScheduleTemplate, days: Int, month: Int, year: Int) = database.applyScheduleTemplate(scheduleTemplate, days, month, year)

}