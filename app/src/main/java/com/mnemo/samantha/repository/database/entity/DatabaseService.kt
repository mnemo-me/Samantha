package com.mnemo.samantha.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnemo.samantha.domain.Service
import kotlinx.serialization.Serializable

@Entity(tableName = "service_table")
@Serializable
data class DatabaseService (

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "price")
        var price: Long,

        @ColumnInfo(name = "time_to_complete")
        var timeToComplete: Int
)

fun DatabaseService.asDomainModel() : Service {
        return Service(
                id = this.id,
                name = this.name,
                price = this.price,
                timeToComplete = this.timeToComplete
        )
}

fun List<DatabaseService>.asDomainModel() : List<Service> {
        return map {
                it.asDomainModel()
        }
}