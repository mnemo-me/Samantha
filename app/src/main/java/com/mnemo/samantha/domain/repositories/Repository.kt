package com.mnemo.samantha.domain.repositories

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.mnemo.samantha.domain.entities.*
import java.io.File


interface Repository {


    val services: LiveData<List<Service>>



    // Appointments
    fun getDaySchedule(date: Int, month: Int, year: Int) : LiveData<List<Appointment>>
    fun getTodayClients(date: Int, month: Int, year: Int) : LiveData<List<Appointment>>
    suspend fun addAppointment(appointment: Appointment)
    suspend fun bookClient(appointmentId: Long, clientId: Long, services: List<Service>, serviceCost: Long, serviceTimeToComplete: Int)
    fun getClientAppointments(clientId: Long) : LiveData<List<Appointment>>
    suspend fun updateAppointmentState(appointmentId: Long, appointmentState: Int)


    // Statistics
    suspend fun getStatistics() : List<Statistics>
    fun getAnnualRevenue(year: Int) : LiveData<Long>


    // Services
    fun getService(serviceId: Long) : LiveData<Service>
    suspend fun addService(service: Service)


    // Schedule
    fun getSchedule() : LiveData<ScheduleTemplate>
    suspend fun addSchedule(scheduleTemplate: ScheduleTemplate)
    suspend fun applyScheduleTemplate(scheduleTemplate: ScheduleTemplate, days: Int, month: Int, year: Int)


    // File storage
    fun getStoragePath() : File?
}