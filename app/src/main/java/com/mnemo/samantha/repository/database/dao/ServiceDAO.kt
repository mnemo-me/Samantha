package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.repository.database.entity.Service

@Dao
interface ServiceDAO {

    @Insert
    fun insert(service: Service)

    @Update
    fun update(service: Service)

    @Delete
    fun delete(service: Service)

    @Query("SELECT * FROM service_table ORDER BY id ASC")
    fun getAll() : LiveData<List<Service>>

    @Query("SELECT * FROM service_table WHERE id = :serviceId")
    fun get(serviceId: Long) : LiveData<Service>
}