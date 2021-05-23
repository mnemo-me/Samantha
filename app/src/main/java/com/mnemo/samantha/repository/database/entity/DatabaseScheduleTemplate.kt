package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnemo.samantha.domain.ScheduleTemplate

@Entity(tableName = "schedule_template_table")
data class DatabaseScheduleTemplate (

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name = "working_time_start")
        var workingTimeStart: Int,

        @ColumnInfo(name = "working_time_end")
        var workingTimeEnd: Int,

        @ColumnInfo(name = "break_time_start")
        var breakTimeStart: Int?,

        @ColumnInfo(name = "break_time_end")
        var breakTimeEnd: Int?,

        @ColumnInfo(name = "time_sector")
        var timeSector: Int
)

fun DatabaseScheduleTemplate.asDomainModel() : ScheduleTemplate{
        return ScheduleTemplate(
                id = this.id,
                workingTimeStart = this.workingTimeStart,
                workingTimeEnd = this.workingTimeEnd,
                breakTimeStart = this.breakTimeStart,
                breakTimeEnd = this.breakTimeEnd,
                timeSector = this.timeSector
        )
}