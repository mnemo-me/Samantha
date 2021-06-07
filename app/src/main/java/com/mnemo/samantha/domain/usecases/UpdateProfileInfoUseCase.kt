package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class UpdateProfileInfoUseCase @Inject constructor(val masterRepository: MasterRepository) {

    suspend operator fun invoke(id: Long, name: String, profession: String, phoneNumber: String) = masterRepository.updateProfileInfo(id, name, profession, phoneNumber)
}