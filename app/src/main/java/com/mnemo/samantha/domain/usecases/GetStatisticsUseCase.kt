package com.mnemo.samantha.domain.usecases

import com.mnemo.samantha.domain.entities.Statistics
import com.mnemo.samantha.domain.repositories.AppointmentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetStatisticsUseCase @Inject constructor(val appointmentsRepository: AppointmentsRepository) {

    suspend operator fun invoke() : List<Statistics>{

        val statistics = mutableListOf<Statistics>()

        withContext(Dispatchers.IO) {
            val workingYears = appointmentsRepository.getWorkingYears()

            workingYears.forEach { year ->
                val workingMonths = appointmentsRepository.getWorkingMonths(year)

                workingMonths.forEach { month ->

                    val workingDaysCount = appointmentsRepository.getWorkingDaysCount(month, year)
                    val clientsCount = appointmentsRepository.getClientsCount(month, year)
                    val revenue = appointmentsRepository.getMonthRevenue(month, year)

                    statistics.add(Statistics(month, year, workingDaysCount, clientsCount, revenue))
                }
            }
        }

        return statistics
    }
}