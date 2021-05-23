package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Client
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddClientDialogViewModel(val appointmentId: Long): ViewModel() {

    @Inject
    lateinit var repository: Repository

    val clients : LiveData<List<Client>>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        clients = repository.clients
    }

    fun bookClient(client: Client, serviceCost: Int){
       viewModelScope.launch{
           repository.bookClient(appointmentId, client, serviceCost)
       }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}