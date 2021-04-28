package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients_table")
data class Client (

    @PrimaryKey(autoGenerate = true)
    var clientId: Long = 0L,

    @ColumnInfo(name = "client_name")
    var clientName: String,

    @ColumnInfo(name = "client_phone_number")
    var clientPhoneNumber: String
)