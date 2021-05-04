package com.mnemo.samantha.repository

import android.content.Context
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.repository.database.entity.Client

class Repository(val database: SamanthaDatabase) {

    companion object{

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(context: Context) : Repository{

            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Repository(SamanthaDatabase.getInstance(context))

                    INSTANCE = instance
                }

                return instance
            }
        }
    }


    // Clients
    fun getClient(clientId: Long) = database.getClient(clientId)

    fun updateClientInfo(client: Client) = database.updateClientInfo(client)

    fun addClient(client: Client) =  database.addClient(client)

    fun removeClient(clientId: Long) = database.removeClient(clientId)

    fun getClientList() = database.getClientList()


    // Schedule
    fun getDaySchedule(date: Int, month: Int, year: Int) = database.getDaySchedule(date, month, year)

    fun bookClient(appointmentId: Long, clientId: Long?, serviceCost: Int?) = database.bookClient(appointmentId, clientId, serviceCost)


}