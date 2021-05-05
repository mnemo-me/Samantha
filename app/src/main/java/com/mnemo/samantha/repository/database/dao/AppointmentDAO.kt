package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mnemo.samantha.repository.database.entity.Appointment

@Dao
interface AppointmentDAO {

    @Insert
    fun insert(appointment: Appointment)

    @Update
    fun update(appointment: Appointment)

    @Query("UPDATE appointments_table SET client_id = 0, client_name = \"\", client_phone_number = \"\", state = :state WHERE id = :id")
    fun updateAppointmentState(id: Long, state: Int)

    @Query("UPDATE appointments_table SET client_id = :clientId, client_name = :clientName, client_phone_number = :clientPhoneNumber, service_cost = :serviceCost, state = :state WHERE id = :id")
    fun bookClient(id: Long, clientId: Long, clientName: String, clientPhoneNumber: String, serviceCost: Int?, state: Int)

    @Query("DELETE FROM appointments_table WHERE id = :id")
    fun remove(id: Long)

    @Query("SELECT * FROM appointments_table WHERE date = :date AND month = :month AND year = :year ORDER BY time ASC")
    fun getDaySchedule(date: Int, month: Int, year: Int) : LiveData<List<Appointment>>

    @Query("SELECT * FROM appointments_table WHERE date = :date AND month = :month AND year = :year AND state = :state")
    fun getTodayClients(date: Int, month: Int, year: Int, state: Int) : LiveData<List<Appointment>>
}