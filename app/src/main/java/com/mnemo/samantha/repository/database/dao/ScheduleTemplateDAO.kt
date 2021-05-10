package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.repository.database.entity.ScheduleTemplate

@Dao
interface ScheduleTemplateDAO {

    @Insert
    fun insert(scheduleTemplate: ScheduleTemplate)

    @Update
    fun update(scheduleTemplate: ScheduleTemplate)

    @Delete
    fun delete(scheduleTemplate: ScheduleTemplate)

    @Query("SELECT * FROM schedule_template_table LIMIT 1")
    fun get() : LiveData<ScheduleTemplate>
}