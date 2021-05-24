package com.mnemo.samantha.di

import com.mnemo.samantha.SamanthaApplication
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.SamanthaDatabase
import com.mnemo.samantha.repository.file_storage.FileStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getRepository() : Repository = Repository.getInstance()

    @Singleton
    @Provides
    fun getDatabase() : SamanthaDatabase = SamanthaApplication.database

    @Singleton
    @Provides
    fun getFileStorage() : FileStorage = SamanthaApplication.fileStorage

}