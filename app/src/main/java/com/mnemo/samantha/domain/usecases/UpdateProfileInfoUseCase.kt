package com.mnemo.samantha.domain.usecases

import android.graphics.Bitmap
import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class UpdateProfileInfoUseCase @Inject constructor(val masterRepository: MasterRepository, val saveMasterAvatarUseCase: SaveMasterAvatarUseCase) {

    suspend fun invoke(id: Long, name: String, profession: String, phoneNumber: String, masterAvatar: Bitmap?) {

        masterRepository.updateProfileInfo(id, name, profession, phoneNumber)

        if (masterAvatar != null) saveMasterAvatarUseCase.invoke(masterAvatar, id)
    }
}