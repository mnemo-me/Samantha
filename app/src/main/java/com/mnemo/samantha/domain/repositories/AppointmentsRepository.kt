package com.mnemo.samantha.domain.repositories

import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.entities.Service
import com.mnemo.samantha.domain.entities.Statistics
import kotlinx.coroutines.flow.Flow

interface AppointmentsRepository {

    suspend fun getDaySchedule(date: Int, month: Int, year: Int) : Flow<List<Appointment>>
    suspend fun getTodayClients(date: Int, month: Int, year: Int) : Flow<List<Appointment>>
    suspend fun addAppointments(appointments: List<Appointment>)
    suspend fun bookClient(appointmentId: Long, client: Client, services: List<Service>, serviceCost: Long, serviceTimeToComplete: Int)
    suspend fun getClientAppointments(clientId: Long) : Flow<List<Appointment>>
    suspend fun updateAppointmentState(appointmentId: Long, appointmentState: Int)
    suspend fun getWorkingYears() : List<Int>
    suspend fun getWorkingMonths(year: Int) : List<Int>
    suspend fun getWorkingDaysCount(month: Int, year: Int) : Int
    suspend fun getClientsCount(month: Int, year: Int) : Int
    suspend fun getMonthRevenue(month: Int, year: Int) : Long
    suspend fun getAnnualRevenue(year: Int) : Long
}