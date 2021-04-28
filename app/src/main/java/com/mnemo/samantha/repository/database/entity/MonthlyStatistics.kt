package com.mnemo.samantha.repository.database.entity

data class MonthlyStatistics (val id: Long, val year: Int, val month: String, val workingDays: Int, val clients: Int, val revenue: Long)