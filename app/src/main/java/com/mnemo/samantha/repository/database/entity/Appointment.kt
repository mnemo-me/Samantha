package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments_table")
data class Appointment (

    @PrimaryKey
    var appointmentId: Long = 0L,

    @ColumnInfo(name = "appointment_tIme")
    var appointmentTime : Int,

    @ColumnInfo(name = "appointment_date")
    var appointmentDate : Int,

    @ColumnInfo(name = "appointment_month")
    var appointmentMonth : Int,

    @ColumnInfo(name = "appointment_year")
    var appointmentYear : Int,

    @ColumnInfo(name = "appointment_client_id")
    var appointmentClient : Long?,

    @ColumnInfo(name = "appointment_summary_cost")
    var appointmentSummaryCost : Int?
)