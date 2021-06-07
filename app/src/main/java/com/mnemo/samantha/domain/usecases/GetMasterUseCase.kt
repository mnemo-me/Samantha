package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class GetMasterUseCase @Inject constructor(val masterRepository: MasterRepository) {

    suspend operator fun invoke() = masterRepository.getMaster()
}