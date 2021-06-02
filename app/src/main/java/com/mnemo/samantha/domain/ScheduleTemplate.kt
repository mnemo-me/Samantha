package com.mnemo.samantha.domain

import com.mnemo.samantha.repository.database.entity.DatabaseScheduleTemplate

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

fun ScheduleTemplate.asDatabaseModel() : DatabaseScheduleTemplate {
    return DatabaseScheduleTemplate(
        id = this.id,
        workingTimeStart = this.workingTimeStart,
        workingTimeEnd = this.workingTimeEnd,
        haveBreak = this.haveBreak,
        breakTimeStart = this.breakTimeStart,
        breakTimeEnd = this.breakTimeEnd,
        timeSector = this.timeSector,
        workingDays = this.workingDays
    )
}