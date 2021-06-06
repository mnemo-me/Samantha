package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class CheckProfileUseCase @Inject constructor(val masterRepository: MasterRepository) {

    suspend fun invoke() = masterRepository.checkProfile()
}