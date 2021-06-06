package com.mnemo.samantha.ui.clients.client_edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.R
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Client
import com.mnemo.samantha.domain.usecases.AddClientUseCase
import com.mnemo.samantha.domain.usecases.GetClientAvatarPathUseCase
import com.mnemo.samantha.domain.usecases.GetClientUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientEditVewModel(val clientId: Long, val appointmentId: Long) : ViewModel() {

    @Inject
    lateinit var getClientUseCase: GetClientUseCase

    @Inject
    lateinit var getClientAvatarPathUseCase: GetClientAvatarPathUseCase

    @Inject
    lateinit var addClientUseCase: AddClientUseCase

    private val _client = MutableLiveData<Client>()
    val client : LiveData<Client>
    get() = _client

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getClientUseCase.invoke(clientId).collect { _client.value = it }
        }
    }

    fun updateClientInfo(clientName: String, clientPhoneNumber: String, clientAvatar: Bitmap?){
        viewModelScope.launch {
            val client = Client(clientId, clientName, clientPhoneNumber)
            addClientUseCase.invoke(client, clientAvatar)
        }
    }

    fun getClientAvatarPath(clientId: Long) = getClientAvatarPathUseCase.invoke(clientId)

    fun getBitmapFromUri(context: Context, uri: Uri) : Bitmap{
        val contentResolver = context.contentResolver
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    fun getDefaultProfileBitmap(context: Context) : Bitmap{
        return BitmapFactory.decodeResource(context.resources, R.drawable.empty_profile)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}