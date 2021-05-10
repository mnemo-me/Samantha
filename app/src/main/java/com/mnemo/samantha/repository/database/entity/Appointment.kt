package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

const val APPOINTMENT_STATE_BREAK = -1
const val APPOINTMENT_STATE_FREE = 0
const val APPOINTMENT_STATE_BUSY = 1

@Entity(tableName = "appointments_table")
data class Appointment (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "time")
    var time : Int,

    @ColumnInfo(name = "date")
    var date : Int,

    @ColumnInfo(name = "month")
    var month : Int,

    @ColumnInfo(name = "year")
    var year : Int,

    @Embedded(prefix = "client_")
    var client: Client,

    @ColumnInfo(name = "service_cost")
    var serviceCost : Int?,

    @ColumnInfo(name = "state")
    var state: Int
)