package com.mnemo.samantha.repository.database.entity

import androidx.room.*
import com.mnemo.samantha.domain.Appointment

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "appointments_table")
data class DatabaseAppointment (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "time")
    var time : Int,

    @ColumnInfo(name = "date")
    var date : Int,

    @ColumnInfo(name = "month")
    var month : Int,

    @ColumnInfo(name = "year")
    var year : Int,

    @Embedded(prefix = "client_")
    var client: DatabaseClient?,

    @ColumnInfo(name = "service_cost")
    var serviceCost : Int?,

    @ColumnInfo(name = "state")
    var state: Int
)

fun DatabaseAppointment.asDomainModel() : Appointment {
    return Appointment(
        id = this.id,
        time = this.time,
        date = this.date,
        month = this.month,
        year = this.year,
        client = this.client?.asDomainModel(),
        serviceCost = this.serviceCost,
        state = this.state
    )
}

fun List<DatabaseAppointment>.asDomainModel() : List<Appointment> {
    return map{
        it.asDomainModel()
    }
}