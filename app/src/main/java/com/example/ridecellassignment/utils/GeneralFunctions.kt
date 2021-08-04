package com.example.ridecellassignment.utils

import com.example.ridecellassignment.R
import com.example.ridecellassignment.modals.PojoMapMarker
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
        val dateStart = startDate.toDate(DATE_FORMAT_2)
        val dateEnd: Date? = if (dueDate.isNullOrEmpty()) {
            currentDate(DATE_FORMAT_2).toDate(DATE_FORMAT_2)
        } else {
            dueDate.toDate(DATE_FORMAT_2)
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


    fun getMarkerLocations() = arrayListOf(
        PojoMapMarker("Mumbai", 19.0760, 72.8777, R.drawable.ic_map_hospital),
        PojoMapMarker("Delhi", 28.7041, 77.1025, R.drawable.ic_map_library),
        PojoMapMarker("Kolkata", 22.5726, 88.3639, R.drawable.ic_map_restuarant),
        PojoMapMarker("Chennai", 13.0827, 80.2707, R.drawable.ic_map_school)
    )


}