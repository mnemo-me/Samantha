package com.mnemo.samantha.repository.database.dao

import androidx.room.*
import com.mnemo.samantha.repository.database.entity.Master

@Dao
interface MasterDAO {

    @Insert
    fun insert(master: Master)

    @Update
    fun update(master: Master)

    @Delete
    fun delete(master: Master)

    @Query("SELECT * FROM master_table LIMIT 1")
    fun get() : Master


}