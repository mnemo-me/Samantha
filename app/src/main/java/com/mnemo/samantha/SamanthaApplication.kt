package com.mnemo.samantha

import android.app.Application
import com.mnemo.samantha.data.database.SamanthaDatabase
import com.mnemo.samantha.data.file_storage.FileStorage

class SamanthaApplication : Application() {

    companion object{
        lateinit var database : SamanthaDatabase
        lateinit var fileStorage: FileStorage
    }

    override fun onCreate() {
        super.onCreate()

        database = SamanthaDatabase.getInstance(this)
        fileStorage = FileStorage.getInstance(this)
    }
}
