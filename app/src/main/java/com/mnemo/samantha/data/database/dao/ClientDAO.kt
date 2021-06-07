package com.mnemo.samantha.data.database.dao

import androidx.room.*
import com.mnemo.samantha.data.database.entities.DatabaseClient
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseClient: DatabaseClient) : Long

    @Query("DELETE FROM clients_table WHERE id = :id")
    suspend fun removeClient(id: Long)

    @Query("SELECT * FROM clients_table WHERE id = :id")
    fun get(id: Long): Flow<DatabaseClient>

    @Query("SELECT * FROM clients_table ORDER BY id ASC")
    fun getAll(): Flow<List<DatabaseClient>>

    @Query("SELECT id FROM clients_table ORDER BY id DESC LIMIT 1")
    suspend fun getNewClientId(): Long

}