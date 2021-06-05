package com.mnemo.samantha.domain.entities


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
