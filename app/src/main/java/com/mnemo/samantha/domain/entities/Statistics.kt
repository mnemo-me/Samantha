package com.mnemo.samantha.domain.entities


data class Statistics (
        val month: Int,
        val year: Int,
        val workingDays: Int,
        val clients: Int,
        val revenue: Long
)
