package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.repository.database.entity.DatabaseScheduleTemplate

@Dao
interface ScheduleTemplateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseScheduleTemplate: DatabaseScheduleTemplate)

    @Delete
    fun delete(databaseScheduleTemplate: DatabaseScheduleTemplate)

    @Query("SELECT * FROM schedule_template_table LIMIT 1")
    fun get() : LiveData<DatabaseScheduleTemplate>
}