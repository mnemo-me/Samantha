package com.mnemo.samantha.ui.create_profile.profile_edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.R
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.Master
import com.mnemo.samantha.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class ProfileEditViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val master : LiveData<Master>

    // Coroutines
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        master = repository.master
    }

    fun updateProfileInfo(id: Long, name: String, profession: String, phoneNumber: String, masterAvatar: Bitmap?){
        viewModelScope.launch {
            repository.updateProfileInfo(id, name, profession, phoneNumber, masterAvatar)
        }
    }

    fun getMasterAvatarPath(masterId: Long) = repository.getMasterAvatarPath(masterId)

    fun saveMasterAvatar(masterAvatar: Bitmap?){
        if (masterAvatar != null) {
            viewModelScope.launch {
                repository.saveMasterAvatar(masterAvatar, 1)
            }
        }
    }

    fun getBitmapFromUri(context: Context, uri: Uri) : Bitmap {
        val contentResolver = context.contentResolver
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    fun getDefaultProfileBitmap(context: Context) : Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.empty_profile)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}