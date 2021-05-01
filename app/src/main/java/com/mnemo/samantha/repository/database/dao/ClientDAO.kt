package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    @Query("SELECT * FROM clients_table WHERE clientId = :key")
    fun get(key: Long): LiveData<Client>

    @Query("DELETE FROM clients_table WHERE clientId = :key")
    fun remove(key: Long)

    @Query("SELECT * FROM clients_table ORDER BY clientId ASC")
    fun getAll(): LiveData<List<Client>>
}