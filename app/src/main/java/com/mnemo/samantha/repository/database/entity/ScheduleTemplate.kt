package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_template_table")
data class ScheduleTemplate (

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