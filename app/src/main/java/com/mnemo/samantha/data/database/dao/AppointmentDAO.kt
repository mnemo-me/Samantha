package com.mnemo.samantha.data.database.dao

import androidx.room.*
import com.mnemo.samantha.data.database.entities.DatabaseAppointment
import com.mnemo.samantha.data.database.entities.DatabaseService
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appointments: List<DatabaseAppointment>)

    @Query("DELETE FROM appointments_table WHERE id = :id")
    suspend fun remove(id: Long)

    @Query("SELECT * FROM appointments_table WHERE date = :date AND month = :month AND year = :year ORDER BY time ASC")
    fun getDaySchedule(date: Int, month: Int, year: Int) : Flow<List<DatabaseAppointment>>

    @Query("SELECT * FROM appointments_table WHERE date = :date AND month = :month AND year = :year AND state = :state")
    fun getTodayClients(date: Int, month: Int, year: Int, state: Int) : Flow<List<DatabaseAppointment>>

    @Query("UPDATE appointments_table SET client_id = :clientId, client_name = :clientName, client_phone_number = :clientPhoneNumber, services = :services, service_cost = :serviceCost, time_to_complete = :timeToComplete, state = :state WHERE id = :id")
    suspend fun bookClient(id: Long, clientId: Long, clientName: String, clientPhoneNumber: String, services: List<DatabaseService>, serviceCost: Long, timeToComplete: Int, state: Int)

    @Query("SELECT * FROM appointments_table WHERE client_id = :clientId ORDER BY id DESC")
    fun getClientAppointments(clientId: Long) : Flow<List<DatabaseAppointment>>

    @Query("UPDATE appointments_table SET client_id = null, client_name = null, client_phone_number = null, state = :state WHERE id = :id")
    suspend fun updateAppointmentState(id: Long, state: Int)

    @Query("SELECT DISTINCT year FROM appointments_table")
    suspend fun getWorkingYears() : List<Int>

    @Query("SELECT DISTINCT month FROM appointments_table WHERE year = :year")
    suspend fun getWorkingMonths(year: Int) : List<Int>

    @Query("SELECT COUNT (DISTINCT date) FROM appointments_table WHERE month = :month AND year = :year")
    suspend fun getWorkingDaysCount(month: Int, year: Int) : Int

    @Query("SELECT COUNT (DISTINCT client_id) FROM appointments_table WHERE month = :month AND year = :year")
    suspend fun getClientsCount(month: Int, year: Int) : Int

    @Query("SELECT SUM (service_cost) FROM appointments_table WHERE month = :month AND year = :year")
    suspend fun getMonthRevenue(month: Int, year: Int) : Long

    @Query("SELECT SUM (service_cost) FROM appointments_table WHERE year = :year")
    suspend fun getAnnualRevenue(year: Int) : Long
}