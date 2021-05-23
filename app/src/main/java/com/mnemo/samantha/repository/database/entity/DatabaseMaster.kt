package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnemo.samantha.domain.Master

@Entity(tableName = "master_table")
data class DatabaseMaster (

       @PrimaryKey(autoGenerate = true)
       var id: Long = 0L,

       @ColumnInfo(name = "name")
       var name: String,

       @ColumnInfo(name = "profession")
       var profession: String,

       @ColumnInfo(name = "phone_number")
       val phoneNumber: String,

       @ColumnInfo(name = "country")
       val country: String,

       @ColumnInfo(name = "city")
       val city: String,

       @ColumnInfo(name = "currency")
       val currency: String,

       @ColumnInfo(name = "private_access")
       val privateAccess: Boolean
)

fun DatabaseMaster.asDomainModel() : Master {
      return Master(
             id = this.id,
             name = this.name,
             profession = this.profession,
             phoneNumber = this.phoneNumber,
             country = this.country,
             city = this.city,
             currency = this.currency,
             privateAccess = this.privateAccess
      )
}