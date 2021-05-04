package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.*

class ClientsViewModel(val repository: Repository) : ViewModel() {

    val clients = repository.getClientList()
    //val clients : LiveData<List<Client>>
    //get() = _clients

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {

        uiScope.launch {

        }
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