package com.mnemo.samantha.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mnemo.samantha.repository.database.dao.*
import com.mnemo.samantha.repository.database.entity.*

@Database(entities = [DatabaseMaster::class, DatabaseClient::class, DatabaseAppointment::class, DatabaseService::class, DatabaseScheduleTemplate::class], version = 1, exportSchema = false)
@TypeConverters(com.mnemo.samantha.util.TypeConverters::class)
abstract class SamanthaDatabase : RoomDatabase() {

    abstract val masterDAO: MasterDAO
    abstract val clientDao: ClientDAO
    abstract val appointmentDAO: AppointmentDAO
    abstract val serviceDAO: ServiceDAO
    abstract val scheduleTemplateDAO: ScheduleTemplateDAO

    companion object{

        @Volatile
        private lateinit var INSTANCE: SamanthaDatabase

        fun getInstance(context: Context): SamanthaDatabase{

            synchronized(this){

                if (!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, SamanthaDatabase::class.java, "samantha_database")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return INSTANCE
            }
        }
    }
}