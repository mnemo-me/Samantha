package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddClientDialogViewModel(val appointmentId: Long): ViewModel() {

    @Inject
    lateinit var repository: Repository

    val clients : LiveData<List<Client>>

    init {
        DaggerAppComponent.create().inject(this)

        clients = repository.getClientList()
    }

    fun bookClient(clientId: Long, serviceCost: Int?){
       CoroutineScope(Dispatchers.IO).launch{
           repository.bookClient(appointmentId, clientId, serviceCost)
       }
    }
}