package com.mnemo.samantha.domain

import com.mnemo.samantha.repository.database.entity.DatabaseMaster

data class Master (
    var id: Long = 0L,
    var name: String,
    var profession: String,
    val phoneNumber: String,
    val country: String,
    val city: String,
    val currency: String,
    val privateAccess: Boolean
)

fun Master.asDatabaseModel() : DatabaseMaster{
    return DatabaseMaster(
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