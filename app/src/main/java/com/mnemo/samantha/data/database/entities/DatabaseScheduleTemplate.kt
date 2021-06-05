package com.mnemo.samantha.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnemo.samantha.domain.entities.ScheduleTemplate

@Entity(tableName = "schedule_template_table")
data class DatabaseScheduleTemplate (

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name = "working_time_start")
        var workingTimeStart: Int,

        @ColumnInfo(name = "working_time_end")
        var workingTimeEnd: Int,

        @ColumnInfo(name = "have_break")
        var haveBreak: Boolean,

        @ColumnInfo(name = "break_time_start")
        var breakTimeStart: Int?,

        @ColumnInfo(name = "break_time_end")
        var breakTimeEnd: Int?,

        @ColumnInfo(name = "time_sector")
        var timeSector: Int,

        @ColumnInfo(name = "working_days")
        var workingDays: List<Int>
)

fun DatabaseScheduleTemplate.asDomainModel() : ScheduleTemplate {
        return ScheduleTemplate(
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