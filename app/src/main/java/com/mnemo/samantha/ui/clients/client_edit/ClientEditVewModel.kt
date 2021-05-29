package com.mnemo.samantha.ui.clients.client_edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.R
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Client
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientEditVewModel(val clientId: Long, val appointmentId: Long, val context: Context) : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val client : LiveData<Client>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        client = repository.getClient(clientId)
    }

    // Update info about client or create new client
    fun updateClientInfo(clientName: String, clientPhoneNumber: String, clientAvatar: Bitmap?){
        viewModelScope.launch {

            val client = Client(clientId, clientName, clientPhoneNumber)
            repository.addClient(client, clientAvatar)

            if (appointmentId != 0L) {
                repository.bookClient(appointmentId, clientId, 700)
            }
        }
    }

    fun getClientAvatarPath(clientId: Long) = repository.getClientAvatarPath(clientId)

    fun getBitmapFromUri(uri: Uri) : Bitmap{
        val contentResolver = context.contentResolver
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    fun getDefaultProfileBitmap() : Bitmap{
        return BitmapFactory.decodeResource(context.resources, R.drawable.empty_profile)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}