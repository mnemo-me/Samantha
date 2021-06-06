package com.mnemo.samantha.ui.create_profile.profile_edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.R
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.usecases.GetMasterAvatarPathUseCase
import com.mnemo.samantha.domain.usecases.GetMasterUseCase
import com.mnemo.samantha.domain.usecases.SaveMasterAvatarUseCase
import com.mnemo.samantha.domain.usecases.UpdateProfileInfoUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ProfileEditViewModel : ViewModel() {

    @Inject
    lateinit var getMasterUseCase: GetMasterUseCase

    @Inject
    lateinit var updateProfileInfoUseCase: UpdateProfileInfoUseCase

    @Inject
    lateinit var getMasterAvatarPathUseCase: GetMasterAvatarPathUseCase

    @Inject
    lateinit var saveMasterAvatarUseCase: SaveMasterAvatarUseCase

    private var _master = MutableLiveData<Master>()
    val master : LiveData<Master>
    get() = _master

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        DaggerAppComponent.create().inject(this)

        viewModelScope.launch {
            getMasterUseCase.invoke().collect { _master.value = it }
        }
    }

    fun updateProfileInfo(id: Long, name: String, profession: String, phoneNumber: String, masterAvatar: Bitmap?){
        viewModelScope.launch {
            updateProfileInfoUseCase.invoke(id, name, profession, phoneNumber, masterAvatar)
        }
    }

    fun getMasterAvatarPath(masterId: Long) = getMasterAvatarPathUseCase.invoke(masterId)

    fun saveMasterAvatar(masterAvatar: Bitmap?){
        if (masterAvatar != null) {
            viewModelScope.launch {
                saveMasterAvatarUseCase.invoke(masterAvatar, 1)
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