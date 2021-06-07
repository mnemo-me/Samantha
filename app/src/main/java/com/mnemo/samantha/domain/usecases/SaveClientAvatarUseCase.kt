package com.mnemo.samantha.domain.usecases

import android.graphics.Bitmap
import com.mnemo.samantha.data.file_storage.FileStorage
import javax.inject.Inject

class SaveClientAvatarUseCase @Inject constructor(val fileStorage: FileStorage) {

    suspend operator fun invoke(bitmap: Bitmap, clientId: Long) {
            fileStorage.saveClientAvatar(bitmap, clientId)
    }
}