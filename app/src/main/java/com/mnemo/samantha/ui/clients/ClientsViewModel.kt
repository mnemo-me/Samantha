package com.mnemo.samantha.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.repository.database.dao.ClientDAO
import com.mnemo.samantha.repository.database.entity.Client
import kotlinx.coroutines.*

class ClientsViewModel(val database: ClientDAO) : ViewModel() {

    private val _clients = MutableLiveData<MutableList<ClientsAdapter.DataItem>>()
    val clients : LiveData<MutableList<ClientsAdapter.DataItem>>
    get() = _clients

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        _clients.value = mutableListOf()
        _clients.value?.add(ClientsAdapter.DataItem.Header)

        uiScope.launch {

            _clients.value?.addAll(getClientsFromDatabase().value.orEmpty().map { client ->
                ClientsAdapter.DataItem.ClientItem(client)
            })
        }
    }



    // Functions that can launch from UI interactions
    fun addClient(client: Client){
        uiScope.launch {
            addClientToDatabase(client)
        }
    }


    // Functions that communicating with database
    private suspend fun getClientsFromDatabase(): LiveData<List<Client>>{
        return withContext(Dispatchers.IO){
            database.getAll()
        }
    }

    private suspend fun addClientToDatabase(client: Client){
        withContext(Dispatchers.IO){
            database.insert(client)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}