package com.mnemo.samantha.domain.entities


data class ScheduleTemplate (
    var id: Long = 0L,
    var workingTimeStart: Int,
    var workingTimeEnd: Int,
    var haveBreak: Boolean,
    var breakTimeStart: Int?,
    var breakTimeEnd: Int?,
    var timeSector: Int,
    var workingDays: List<Int>
)

