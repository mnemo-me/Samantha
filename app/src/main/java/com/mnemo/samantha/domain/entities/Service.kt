package com.mnemo.samantha.domain.entities


data class Service (
    var id: Long = 0L,
    var name: String,
    var price: Long,
    var timeToComplete: Int
)

fun List<Service>.convertToString() : String{

    var servicesString = ""

    for(service in this){
        servicesString = servicesString + service.name + ", "
    }

    servicesString = servicesString.dropLast(2)

    return servicesString
}