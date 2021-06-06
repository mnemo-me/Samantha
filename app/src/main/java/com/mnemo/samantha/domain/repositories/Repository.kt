package com.mnemo.samantha.domain.repositories

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.mnemo.samantha.domain.entities.*
import java.io.File


interface Repository {


    val services: LiveData<List<Service>>



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