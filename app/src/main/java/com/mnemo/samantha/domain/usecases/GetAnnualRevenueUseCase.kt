package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import javax.inject.Inject

class GetAnnualRevenueUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend operator fun invoke(year: Int) = appointmentsRepository.getAnnualRevenue(year)
}