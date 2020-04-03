package org.msarpong.openweather.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

val LOCALE = Locale.setDefault(Locale.ITALIAN)

fun getDate(pattern: String): String {
    val currentDate = LocalDate.now()
    return currentDate.format(DateTimeFormatter.ofPattern(pattern))
}