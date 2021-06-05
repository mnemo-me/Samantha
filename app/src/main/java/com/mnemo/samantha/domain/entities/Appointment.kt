package com.mnemo.samantha.domain.entities


const val APPOINTMENT_STATE_BREAK = -1
const val APPOINTMENT_STATE_FREE = 0
const val APPOINTMENT_STATE_BUSY = 1
const val APPOINTMENT_STATE_OVERLAY = 2

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
