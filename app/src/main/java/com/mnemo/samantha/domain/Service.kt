package com.mnemo.samantha.domain

import com.mnemo.samantha.repository.database.entity.DatabaseService

data class Service (
    var id: Long = 0L,
    var name: String,
    var price: Long,
    var timeToComplete: Int
)

fun Service.asDatabaseModel() : DatabaseService{
    return DatabaseService(
        id = this.id,
        name = this.name,
        price = this.price,
        timeToComplete = this.timeToComplete
    )
}

fun List<Service>.asDatabaseModel() : List<DatabaseService>{
    return map {
        it.asDatabaseModel()
    }
}

fun List<Service>.convertToString() : String{

    var servicesString = ""

    for(service in this){
        servicesString = servicesString + service.name + ", "
    }

    servicesString = servicesString.dropLast(2)

    return servicesString
}