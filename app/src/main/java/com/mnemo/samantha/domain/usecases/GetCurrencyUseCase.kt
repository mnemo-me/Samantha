package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.MasterRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(val masterRepository: MasterRepository) {

    suspend fun invoke() = masterRepository.getCurrency()
}