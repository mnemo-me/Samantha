package com.mnemo.samantha.di

import com.mnemo.samantha.SamanthaApplication
import com.mnemo.samantha.data.database.SamanthaDatabase
import com.mnemo.samantha.data.database.dao.*
import com.mnemo.samantha.data.file_storage.FileStorage
import com.mnemo.samantha.data.repositories.*
import com.mnemo.samantha.domain.repositories.*
import com.mnemo.samantha.domain.usecases.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {


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
    fun getUpdateProfileInfoUseCase() : UpdateProfileInfoUseCase = UpdateProfileInfoUseCase(getMasterRepository())

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
    fun getAddClientUseCase() : AddClientUseCase = AddClientUseCase(getClientsRepository())

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
    fun getRemoveClientUseCase() : RemoveClientUseCase = RemoveClientUseCase(getClientsRepository())

    @Singleton
    @Provides
    fun getSaveClientAvatarUseCase() : SaveClientAvatarUseCase = SaveClientAvatarUseCase(getFileStorage())


    // Appointments
    @Singleton
    @Provides
    fun getAppointmentDAO() : AppointmentDAO = getDatabase().appointmentDAO

    @Singleton
    @Provides
    fun getAppointmentsRepository() : AppointmentsRepository = AppointmentsRepositoryImpl.getInstance(getAppointmentDAO())

    @Singleton
    @Provides
    fun getAddAppointmentsUseCase() : AddAppointmentsUseCase = AddAppointmentsUseCase(getAppointmentsRepository())

    @Singleton
    @Provides
    fun getBookClientUseCase() : BookClientUseCase = BookClientUseCase(getAppointmentsRepository(), getClientsRepository(), getGetClientUseCase())

    @Singleton
    @Provides
    fun getGetAnnualRevenueUseCase() : GetAnnualRevenueUseCase = GetAnnualRevenueUseCase(getAppointmentsRepository())

    @Singleton
    @Provides
    fun getGetClientAppointmentsUseCase() : GetClientAppointmentsUseCase = GetClientAppointmentsUseCase(getAppointmentsRepository())

    @Singleton
    @Provides
    fun getGetDayScheduleUseCase() : GetDayScheduleUseCase = GetDayScheduleUseCase(getAppointmentsRepository())

    @Singleton
    @Provides
    fun getGetStatisticsUseCase() : GetStatisticsUseCase = GetStatisticsUseCase(getAppointmentsRepository())

    @Singleton
    @Provides
    fun getGetTodayClientsUseCase() : GetTodayClientsUseCase = GetTodayClientsUseCase(getAppointmentsRepository())

    @Singleton
    @Provides
    fun getUpdateAppointmentStateUseCase() : UpdateAppointmentStateUseCase = UpdateAppointmentStateUseCase(getAppointmentsRepository())


    // Services
    @Singleton
    @Provides
    fun getServiceDAO() : ServiceDAO = getDatabase().serviceDAO

    @Singleton
    @Provides
    fun getServicesRepository() : ServicesRepository = ServicesRepositoryImpl.getInstance(getServiceDAO())

    @Singleton
    @Provides
    fun getAddServiceUseCase() : AddServiceUseCase = AddServiceUseCase(getServicesRepository())

    @Singleton
    @Provides
    fun getGetServicesUseCase() : GetServicesUseCase = GetServicesUseCase(getServicesRepository())

    @Singleton
    @Provides
    fun getGetServiceUseCase() : GetServiceUseCase = GetServiceUseCase(getServicesRepository())


    // Schedule
    @Singleton
    @Provides
    fun getScheduleTemplateDAO() : ScheduleTemplateDAO = getDatabase().scheduleTemplateDAO

    @Singleton
    @Provides
    fun getScheduleTemplateRepository() : ScheduleTemplateRepository = ScheduleTemplateRepositoryImpl.getInstance(getScheduleTemplateDAO())

    @Singleton
    @Provides
    fun getAddScheduleUseCase() : AddScheduleUseCase = AddScheduleUseCase(getScheduleTemplateRepository())

    @Singleton
    @Provides
    fun getGetScheduleUseCase() : GetScheduleUseCase = GetScheduleUseCase(getScheduleTemplateRepository())

    @Singleton
    @Provides
    fun getApplyScheduleUseCase() : ApplyScheduleUseCase = ApplyScheduleUseCase(getAddAppointmentsUseCase())

}