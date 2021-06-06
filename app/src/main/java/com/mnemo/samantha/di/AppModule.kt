package com.mnemo.samantha.di

import com.mnemo.samantha.SamanthaApplication
import com.mnemo.samantha.data.SamanthaRepository
import com.mnemo.samantha.data.database.SamanthaDatabase
import com.mnemo.samantha.data.database.dao.ClientDAO
import com.mnemo.samantha.data.database.dao.MasterDAO
import com.mnemo.samantha.data.file_storage.FileStorage
import com.mnemo.samantha.data.repositories.ClientsRepositoryImpl
import com.mnemo.samantha.data.repositories.MasterRepositoryImpl
import com.mnemo.samantha.domain.repositories.ClientsRepository
import com.mnemo.samantha.domain.repositories.MasterRepository
import com.mnemo.samantha.domain.repositories.Repository
import com.mnemo.samantha.domain.usecases.*
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


    // Master
    @Singleton
    @Provides
    fun getMasterDAO() : MasterDAO = getDatabase().masterDAO

    @Singleton
    @Provides
    fun getMasterRepository() : MasterRepository = MasterRepositoryImpl.getInstance(getMasterDAO())

    @Singleton
    @Provides
    fun getCheckProfileUseCase() : CheckProfileUseCase = CheckProfileUseCase(getMasterRepository())

    @Singleton
    @Provides
    fun getCreateProfileUseCase() : CreateProfileUseCase = CreateProfileUseCase(getMasterRepository())

    @Singleton
    @Provides
    fun getGetCurrencyUseCase() : GetCurrencyUseCase = GetCurrencyUseCase(getMasterRepository())

    @Singleton
    @Provides
    fun getGetMasterAvatarPathUseCase() : GetMasterAvatarPathUseCase = GetMasterAvatarPathUseCase(getFileStorage())

    @Singleton
    @Provides
    fun getGetMasterUseCase() : GetMasterUseCase = GetMasterUseCase((getMasterRepository()))

    @Singleton
    @Provides
    fun getSaveMasterAvatarUseCase() : SaveMasterAvatarUseCase = SaveMasterAvatarUseCase(getFileStorage())

    @Singleton
    @Provides
    fun getUpdateProfileInfoUseCase() : UpdateProfileInfoUseCase = UpdateProfileInfoUseCase(getMasterRepository(), getSaveMasterAvatarUseCase())

    @Singleton
    @Provides
    fun getUpdateProfileRegionInfoUseCase() : UpdateProfileRegionInfoUseCase = UpdateProfileRegionInfoUseCase(getMasterRepository())


    // Clients
    @Singleton
    @Provides
    fun getClientDAO() : ClientDAO = getDatabase().clientDao

    @Singleton
    @Provides
    fun getClientsRepository() : ClientsRepository = ClientsRepositoryImpl.getInstance(getClientDAO())

    @Singleton
    @Provides
    fun getAddClientUseCase() : AddClientUseCase = AddClientUseCase(getClientsRepository(), getNewClientIdUseCase(), getSaveClientAvatarUseCase())

    @Singleton
    @Provides
    fun getGetClientAvatarPathUseCase() : GetClientAvatarPathUseCase = GetClientAvatarPathUseCase(getFileStorage())

    @Singleton
    @Provides
    fun getGetClientsUseCase() : GetClientsUseCase = GetClientsUseCase(getClientsRepository())

    @Singleton
    @Provides
    fun getGetClientUseCase() : GetClientUseCase = GetClientUseCase(getClientsRepository())

    @Singleton
    @Provides
    fun getNewClientIdUseCase() : GetNewClientIdUseCase = GetNewClientIdUseCase(getClientsRepository())

    @Singleton
    @Provides
    fun getRemoveClientUseCase() : RemoveClientUseCase = RemoveClientUseCase(getClientsRepository())

    @Singleton
    @Provides
    fun getSaveClientAvatarUseCase() : SaveClientAvatarUseCase = SaveClientAvatarUseCase(getFileStorage())
}