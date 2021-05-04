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

    @Query("UPDATE appointments_table SET appointment_client_id = :clientId, appointment_summary_cost = :appointSummaryCost WHERE appointmentId = :appointmentId")
    fun bookClient(appointmentId: Long, clientId: Long?, appointSummaryCost: Int?)

    @Query("DELETE FROM appointments_table WHERE appointmentId = :appointmentId")
    fun remove(appointmentId: Long)

    @Query("SELECT * FROM appointments_table WHERE appointment_date = :date AND appointment_month = :month AND appointment_year = :year ORDER BY appointment_time ASC")
    fun getDaySchedule(date: Int, month: Int, year: Int) : LiveData<List<Appointment>>
}