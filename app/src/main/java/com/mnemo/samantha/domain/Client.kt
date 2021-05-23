package com.mnemo.samantha.domain

import com.mnemo.samantha.repository.database.entity.DatabaseClient

data class Client (
    var id: Long = 0L,
    var name: String,
    var phoneNumber: String
)

fun Client.asDatabaseModel() : DatabaseClient{
    return DatabaseClient(
            id = this.id,
            name = this.name,
            phoneNumber = this.phoneNumber
    )
}
