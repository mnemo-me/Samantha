package com.mnemo.samantha.di

import com.mnemo.samantha.SamanthaApplication
import com.mnemo.samantha.data.SamanthaRepository
import com.mnemo.samantha.data.database.SamanthaDatabase
import com.mnemo.samantha.data.file_storage.FileStorage
import com.mnemo.samantha.domain.repositories.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getRepository() : Repository = SamanthaRepository.getInstance()

    @Singleton
    @Provides
    fun getDatabase() : SamanthaDatabase = SamanthaApplication.database

    @Singleton
    @Provides
    fun getFileStorage() : FileStorage = SamanthaApplication.fileStorage

}