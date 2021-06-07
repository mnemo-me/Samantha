package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.Master
import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(val masterRepository: MasterRepository) {

    suspend operator fun invoke(master: Master) = masterRepository.createProfile(master)
}