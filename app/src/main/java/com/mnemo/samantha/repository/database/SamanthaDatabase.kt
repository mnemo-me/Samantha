package com.mnemo.samantha.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client

@Database(entities = [Client::class], version = 1, exportSchema = false)
abstract class SamanthaDatabase : RoomDatabase() {

    abstract val clientDao: ClientDAO

    companion object{

        @Volatile
        private var INSTANCE: SamanthaDatabase? = null

        fun getInstance(context: Context): SamanthaDatabase{

            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, SamanthaDatabase::class.java, "samantha_database")
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}