package com.example.testapplication.retrofit.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val calendar = Calendar.getInstance()

    fun getLastDay(): String {
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val currentDate = calendar.time
        return dateFormat.format(currentDate)
    }

    fun getLastMonth(): String {
        calendar.add(Calendar.MONTH, -1)
        val lastMonthDate = calendar.time
        return dateFormat.format(lastMonthDate)
    }

    fun getLastWeek(): String {
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        val lastWeekDate = calendar.time
        return dateFormat.format(lastWeekDate)
    }
}