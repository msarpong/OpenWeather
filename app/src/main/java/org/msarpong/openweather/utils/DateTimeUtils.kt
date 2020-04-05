package org.msarpong.openweather.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

val LOCALE = Locale.setDefault(Locale.ITALIAN)

fun getDate(pattern: String): String {
    val currentDate = LocalDate.now()
    return currentDate.format(DateTimeFormatter.ofPattern(pattern))
}

fun comparisonHour(currentHour: String = getHour(), sunset: String = "18:00"): Boolean {
    val isBefore: Boolean = LocalTime.parse(currentHour).isBefore(LocalTime.parse(sunset))
    return isBefore
}

fun getHour(): String {
    val currenTime = LocalTime.now()
    return currenTime.format(DateTimeFormatter.ofPattern(HOUR))
}

fun convertTimestamp(timestamp: Long): String {
    val fromUnixTimestamp: Instant = Instant.ofEpochSecond(timestamp)
    return fromUnixTimestamp.toString()
}