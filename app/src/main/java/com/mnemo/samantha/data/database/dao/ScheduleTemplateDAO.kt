package com.mnemo.samantha.data.database.dao

import androidx.room.*
import com.mnemo.samantha.data.database.entities.DatabaseScheduleTemplate
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleTemplateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseScheduleTemplate: DatabaseScheduleTemplate)

    @Delete
    suspend fun delete(databaseScheduleTemplate: DatabaseScheduleTemplate)

    @Query("SELECT * FROM schedule_template_table LIMIT 1")
    fun get() : Flow<DatabaseScheduleTemplate>
}