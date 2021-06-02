package com.mnemo.samantha.util

import androidx.room.TypeConverter
import com.mnemo.samantha.repository.database.entity.DatabaseService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TypeConverters {

    @TypeConverter
    fun servicesToString(services: List<DatabaseService>?) = Json.encodeToString(services)

    @TypeConverter
    fun stringToServices(servicesString: String?) = servicesString?.let {
        Json.decodeFromString<List<DatabaseService>?>(
            it
        )
    }

    @TypeConverter
    fun workingDaysToString(workingDays: List<Int>) = Json.encodeToString(workingDays)

    @TypeConverter
    fun stringToWorkingDays(workingDaysString: String) = workingDaysString.let{
        Json.decodeFromString<List<Int>>(
            it
        )
    }
}