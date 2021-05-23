package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnemo.samantha.domain.Client

@Entity(tableName = "clients_table")
data class DatabaseClient (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String
)

fun DatabaseClient.asDomainModel(): Client{
    return Client(
            id = this.id,
            name = this.name,
            phoneNumber = this.phoneNumber
        )

}

fun List<DatabaseClient>.asDomainModel(): List<Client>{
    return map{
        it.asDomainModel()
    }
}