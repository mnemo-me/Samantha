package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddClientDialogViewModel(val appointmentId: Long, val repository: Repository): ViewModel() {

    val clients = repository.getClientList()

    fun bookClient(clientId: Long?, serviceCost: Int?){
       CoroutineScope(Dispatchers.IO).launch{
           repository.bookClient(appointmentId, clientId, serviceCost)
       }
    }
}