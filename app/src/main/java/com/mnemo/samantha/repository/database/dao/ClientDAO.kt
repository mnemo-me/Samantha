package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mnemo.samantha.repository.database.entity.Client

@Dao
interface ClientDAO {

    @Insert
    fun insert(client: Client)

    @Update
    fun update(client: Client)

    @Query("SELECT * FROM clients_table WHERE id = :id")
    fun getClient(id: Long): Client

    @Query("SELECT * FROM clients_table ORDER BY id DESC LIMIT 1")
    fun getLastAddedClient() : Client

    @Query("SELECT * FROM clients_table WHERE id = :id")
    fun get(id: Long): LiveData<Client>

    @Query("DELETE FROM clients_table WHERE id = :id")
    fun remove(id: Long)

    @Query("SELECT * FROM clients_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Client>>
}