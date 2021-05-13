package com.mnemo.samantha.ui.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnemo.samantha.di.DaggerAppComponent
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.data.Statistics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StatisticsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    val statistics = MutableLiveData<MutableList<Statistics>>()

    init {

        DaggerAppComponent.create().inject(this)

        val statisticsValue = mutableListOf<Statistics>()

        CoroutineScope(Dispatchers.IO).launch {

            val workingYears = repository.getWorkingYears()

            workingYears.forEach { year ->
                val workingMonths = repository.getWorkingMonths(year)

                workingMonths.forEach { month ->

                    val workingDaysCount = repository.getWorkingDaysCount(month, year)
                    val clientsCount = repository.getClientsCount(month, year)
                    val revenue = repository.getMonthRevenue(month, year)

                    withContext(Dispatchers.Main) {
                        statisticsValue.add(Statistics(month, year, workingDaysCount, clientsCount, revenue))
                    }
                }
            }

            withContext(Dispatchers.Main){
                statistics.value = statisticsValue
            }
        }
    }
}