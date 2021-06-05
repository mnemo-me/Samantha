package com.mnemo.samantha.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.data.database.entities.DatabaseService

@Dao
interface ServiceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseService: DatabaseService)

    @Delete
    fun delete(databaseService: DatabaseService)

    @Query("SELECT * FROM service_table WHERE id = :serviceId")
    fun get(serviceId: Long) : LiveData<DatabaseService>

    @Query("SELECT * FROM service_table ORDER BY id ASC")
    fun getAll() : LiveData<List<DatabaseService>>

}