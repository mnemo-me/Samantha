package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class UpdateProfileRegionInfoUseCase @Inject constructor(val masterRepository: MasterRepository) {

    suspend fun invoke(id: Long, country: String, city: String, currency: String) = masterRepository.updateProfileRegionInfo(id, country, city, currency)
}