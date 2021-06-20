package com.sample.rgtest.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Mukesh
 * This class is collection of extensions function that has been used in this application
 */

/**
 * Convert the date string to Date object
 * @param dateFormat
 * @return Date
 */
fun String.toDate(dateFormat: String = "EEE, dd MMM yyyy HH:mm:ss"): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    return parser.parse(this)
}

/**
 * @param dateFormat
 * @param timeZone
 * This method will format the the Date object to given @param dateFormat
 */
fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}