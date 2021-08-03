package com.example.ridecellassignment.utils

import java.text.SimpleDateFormat
import java.util.*


object GeneralFunctions {

    fun convertDate(
        iFormat: String, inputDate: String?, oFormat: String, tzIn: TimeZone? = null,
        tzOut: TimeZone? = null
    ): String? {
        if (!inputDate.isNullOrEmpty()) {
            return try {
                val inputFormat = SimpleDateFormat(iFormat, Locale.getDefault())
                val outputFormat = SimpleDateFormat(oFormat, Locale.getDefault())
                tzIn?.let { inputFormat.timeZone = it }
                tzOut?.let { outputFormat.timeZone = it }
                val date: Date? = inputFormat.parse(inputDate)
                if (date != null) (outputFormat.format(date)) else inputDate
            } catch (e: Exception) {
                ""
            }
        }
        return inputDate
    }

    fun currentDate(format: String, timeZone: TimeZone? = null): String {
        val dateFormat = format.formatDate()
        timeZone?.let { dateFormat.timeZone = it }
        return dateFormat.format(Calendar.getInstance().time)

    }


    fun calculateDays(startDate: String, dueDate: String? = null): String {
        val dateStart = startDate.toDate(DATE_FORMAT_1)
        val dateEnd: Date? = if (dueDate.isNullOrEmpty()) {
            currentDate(DATE_FORMAT_1).toDate(DATE_FORMAT_1)
        } else {
            dueDate.toDate(DATE_FORMAT_1)
        }
        val days = if (dateStart != null && dateEnd != null) {
            if (dateEnd.before(dateStart)) {
                0
            } else {
                (dateEnd.time - dateStart.time) / (24 * 60 * 60 * 1000)
            }
        } else 0
        return "$days days"
    }


}