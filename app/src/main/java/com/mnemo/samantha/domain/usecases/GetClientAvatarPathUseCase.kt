package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.data.file_storage.FileStorage
import javax.inject.Inject

class GetClientAvatarPathUseCase @Inject constructor(val fileStorage: FileStorage) {

    fun invoke(clientId: Long) = fileStorage.getClientAvatarPath(clientId)
}