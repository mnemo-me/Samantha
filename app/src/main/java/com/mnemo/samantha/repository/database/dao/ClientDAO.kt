package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.repository.database.entity.DatabaseClient

@Dao
interface ClientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseClient: DatabaseClient)

    @Query("DELETE FROM clients_table WHERE id = :id")
    fun removeClient(id: Long)

    @Query("SELECT * FROM clients_table WHERE id = :id")
    fun get(id: Long): LiveData<DatabaseClient>

    @Query("SELECT * FROM clients_table ORDER BY id ASC")
    fun getAll(): LiveData<List<DatabaseClient>>

}