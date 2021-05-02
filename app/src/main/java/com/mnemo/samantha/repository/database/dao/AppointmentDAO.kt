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

    @Query("DELETE FROM appointments_table WHERE appointmentId = :appointmentId")
    fun remove(appointmentId: Long)

    @Query("SELECT * FROM appointments_table WHERE appointment_day = :day AND appointment_month = :month AND appointment_year = :year ORDER BY appointment_tIme ASC")
    fun getDaySchedule(day: Int, month: Int, year: Int) : LiveData<List<Appointment>>
}