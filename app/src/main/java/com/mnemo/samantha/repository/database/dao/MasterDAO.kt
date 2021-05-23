package com.mnemo.samantha.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mnemo.samantha.repository.database.entity.DatabaseMaster

@Dao
interface MasterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseMaster: DatabaseMaster)

    @Delete
    fun delete(databaseMaster: DatabaseMaster)

    @Query("SELECT * FROM master_table LIMIT 1")
    fun get() : LiveData<DatabaseMaster>

    @Query("SELECT currency FROM master_table LIMIT 1")
    fun getCurrency() : LiveData<String>

    @Query("SELECT COUNT(id) FROM master_table")
    fun getCount() : Int

}