package com.mnemo.samantha.domain.usecases

import android.graphics.Bitmap
import com.mnemo.samantha.data.file_storage.FileStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveMasterAvatarUseCase @Inject constructor(val fileStorage: FileStorage?) {

    suspend operator fun invoke(bitmap: Bitmap, masterId: Long){
        withContext(Dispatchers.IO){
            fileStorage?.saveMasterAvatar(bitmap, masterId)
        }
    }
}