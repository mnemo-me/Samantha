package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.*
import javax.inject.Inject

class ClientsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val clients : LiveData<List<Client>>
    //val clients : LiveData<List<Client>>
    //get() = _clients

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        DaggerAppComponent.create().inject(this)

        clients = repository.getClientList()
    }



    // Functions that can launch from UI interactions
    fun addClient(client: Client){
        uiScope.launch {
            addClientToDatabase(client)
        }
    }


    private suspend fun addClientToDatabase(client: Client){
        withContext(Dispatchers.IO){
            repository.addClient(client)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}