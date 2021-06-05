package com.mnemo.samantha.domain.repositories

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.mnemo.samantha.domain.entities.*
import java.io.File


interface Repository {


    val master: LiveData<Master>
    val clients: LiveData<List<Client>>
    val services: LiveData<List<Service>>


    // Profile
    suspend fun checkProfile() : Boolean
    suspend fun createProfile(master: Master)
    suspend fun updateProfileInfo(id: Long, name: String, profession: String, phoneNumber: String, masterAvatar: Bitmap?)
    suspend fun saveMasterAvatar(bitmap: Bitmap, masterId: Long)
    fun getMasterAvatarPath(masterId: Long) : File
    suspend fun updateProfileRegionInfo(id: Long, country: String, city: String, currency: String)
    fun getCurrency() : LiveData<String>


    // Clients
    fun getClient(clientId: Long) : LiveData<Client>
    suspend fun addClient(client: Client, clientAvatar: Bitmap?)
    suspend fun removeClient(clientId: Long)
    suspend fun saveClientAvatar(bitmap: Bitmap, clientId: Long)
    fun getClientAvatarPath(clientId: Long) : File


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