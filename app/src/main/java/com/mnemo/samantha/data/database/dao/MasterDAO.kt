package com.mnemo.samantha.data.database.dao

import androidx.room.*
import com.mnemo.samantha.data.database.entities.DatabaseMaster
import kotlinx.coroutines.flow.Flow

@Dao
interface MasterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseMaster: DatabaseMaster)

    @Delete
    fun delete(databaseMaster: DatabaseMaster)

    @Query("SELECT * FROM master_table LIMIT 1")
    fun get() : Flow<DatabaseMaster>

    @Query("SELECT currency FROM master_table LIMIT 1")
    fun getCurrency() : Flow<String>

    @Query("SELECT COUNT(id) FROM master_table")
    fun getCount() : Int

    @Query("UPDATE master_table SET name = :name, profession = :profession, phone_number = :phoneNumber WHERE id = :id")
    fun updateMasterInfo(id: Long, name: String, profession: String, phoneNumber: String)

    @Query("UPDATE master_table SET country = :country, city = :city, currency = :currency WHERE id = :id")
    fun updateRegionInfo(id: Long, country: String, city: String, currency: String)
}