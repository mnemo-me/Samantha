package com.mnemo.samantha.data.database.dao

import androidx.room.*
import com.mnemo.samantha.data.database.entities.DatabaseService
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseService: DatabaseService)

    @Delete
    suspend fun delete(databaseService: DatabaseService)

    @Query("SELECT * FROM service_table WHERE id = :serviceId")
    fun get(serviceId: Long) : Flow<DatabaseService>

    @Query("SELECT * FROM service_table ORDER BY id ASC")
    fun getAll() : Flow<List<DatabaseService>>

}