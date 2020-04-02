package org.msarpong.openweather.datamapping

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)