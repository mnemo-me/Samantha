package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.repository.database.entity.DatabaseAppointment
import com.mnemo.samantha.repository.database.entity.DatabaseClient

@Dao
interface AppointmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseAppointment: DatabaseAppointment)

    @Query("DELETE FROM appointments_table WHERE id = :id")
    fun remove(id: Long)

    @Query("SELECT * FROM appointments_table WHERE date = :date AND month = :month AND year = :year ORDER BY time ASC")
    fun getDaySchedule(date: Int, month: Int, year: Int) : LiveData<List<DatabaseAppointment>>

    @Query("SELECT * FROM appointments_table WHERE date = :date AND month = :month AND year = :year AND state = :state")
    fun getTodayClients(date: Int, month: Int, year: Int, state: Int) : LiveData<List<DatabaseAppointment>>

    @Query("UPDATE appointments_table SET client_id = :clientId, client_name = :clientName, client_phone_number = :clientPhoneNumber, service_cost = :serviceCost, state = :state WHERE id = :id")
    fun bookClient(id: Long, clientId: Long, clientName: String, clientPhoneNumber: String, serviceCost: Int, state: Int)

    @Query("UPDATE appointments_table SET client_id = null, client_name = null, client_phone_number = null, state = :state WHERE id = :id")
    fun updateAppointmentState(id: Long, state: Int)

    @Query("SELECT DISTINCT year FROM appointments_table")
    fun getWorkingYears() : List<Int>

    @Query("SELECT DISTINCT month FROM appointments_table WHERE year = :year")
    fun getWorkingMonths(year: Int) : List<Int>

    @Query("SELECT COUNT (DISTINCT date) FROM appointments_table WHERE month = :month AND year = :year")
    fun getWorkingDaysCount(month: Int, year: Int) : Int

    @Query("SELECT COUNT (DISTINCT client_id) FROM appointments_table WHERE month = :month AND year = :year AND client_id != null")
    fun getClientsCount(month: Int, year: Int) : Int

    @Query("SELECT SUM (service_cost) FROM appointments_table WHERE month = :month AND year = :year")
    fun getMonthRevenue(month: Int, year: Int) : Long
}