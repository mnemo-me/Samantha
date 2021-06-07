package com.mnemo.samantha.di

import com.mnemo.samantha.ui.MainActivityViewModel
import com.mnemo.samantha.ui.clients.ClientsViewModel
import com.mnemo.samantha.ui.clients.client_edit.ClientEditVewModel
import com.mnemo.samantha.ui.clients.client_info.ClientInfoViewModel
import com.mnemo.samantha.ui.create_profile.create_schedule.CreateScheduleViewModel
import com.mnemo.samantha.ui.create_profile.profile_edit.ProfileEditViewModel
import com.mnemo.samantha.ui.create_profile.select_region.SelectRegionViewModel
import com.mnemo.samantha.ui.create_profile.services.ServicesViewModel
import com.mnemo.samantha.ui.create_profile.services.service_edit.ServiceEditViewModel
import com.mnemo.samantha.ui.monthly_schedule.day_schedule.DayScheduleViewModel
import com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.AddClientDialogViewModel
import com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.choose_services.ChooseServicesViewModel
import com.mnemo.samantha.ui.profile.ProfileViewModel
import com.mnemo.samantha.ui.statistics.StatisticsViewModel
import com.mnemo.samantha.ui.today.TodayViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(todayViewModel: TodayViewModel)
    fun inject(dayScheduleViewModel: DayScheduleViewModel)
    fun inject(addClientDialogViewModel: AddClientDialogViewModel)
    fun inject(clientsViewModel: ClientsViewModel)
    fun inject(clientEditVewModel: ClientEditVewModel)
    fun inject(clientInfoViewModel: ClientInfoViewModel)
    fun inject(statisticsViewModel: StatisticsViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(profileEditViewModel: ProfileEditViewModel)
    fun inject(createScheduleViewModel: CreateScheduleViewModel)
    fun inject(selectRegionViewModel: SelectRegionViewModel)
    fun inject(serviceEditViewModel: ServiceEditViewModel)
    fun inject(servicesViewModel: ServicesViewModel)
    fun inject(chooseServicesViewModel: ChooseServicesViewModel)
}