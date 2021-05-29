package com.mnemo.samantha.domain

import androidx.room.Entity
import com.mnemo.samantha.repository.database.entity.DatabaseAppointment

const val APPOINTMENT_STATE_BREAK = -1
const val APPOINTMENT_STATE_FREE = 0
const val APPOINTMENT_STATE_BUSY = 1
const val APPOINTMENT_STATE_OVERLAY = 2

@Entity(tableName = "appointments_table")
data class Appointment (
    var id: Long = 0L,
    var time: Int,
    var date: Int,
    var month: Int,
    var year: Int,
    var client: Client?,
    var services: List<Service>?,
    var serviceCost: Int?,
    var timeToComplete: Int?,
    var state: Int
)

fun Appointment.asDatabaseModel() : DatabaseAppointment {
    return DatabaseAppointment(
        id = this.id,
        time = this.time,
        date = this.date,
        month = this.month,
        year = this.year,
        client = this.client?.asDatabaseModel(),
        services = this.services?.asDatabaseModel(),
        serviceCost = this.serviceCost,
        timeToComplete = this.timeToComplete,
        state = this.state
    )
}