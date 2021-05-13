package com.mnemo.samantha

import android.app.Application
import com.mnemo.samantha.repository.database.SamanthaDatabase

class SamanthaApplication : Application() {

    companion object{
        lateinit var database : SamanthaDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = SamanthaDatabase.getInstance(this)
    }
}
