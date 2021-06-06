package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.data.file_storage.FileStorage
import javax.inject.Inject

class GetMasterAvatarPathUseCase @Inject constructor(val fileStorage: FileStorage) {

    fun invoke(masterId: Long) = fileStorage.getMasterAvatarPath(masterId)
}