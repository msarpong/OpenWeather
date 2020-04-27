package org.msarpong.openweather.service


import android.util.Log
import org.msarpong.openweather.datamapping.WeatherResponse
import org.msarpong.openweather.di.retrofit
import org.msarpong.openweather.utils.API_KEY
import org.msarpong.openweather.utils.UNIT_METRIC
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

sealed class WeatherResult {
    data class Error(val error: Throwable) : WeatherResult()
    data class Success(val weatherList: WeatherResponse) : WeatherResult()
}

interface WeatherReceiver {
    fun receive(result: WeatherResult)
}

class WeatherService {
    private val service: WeatherApi = retrofit.create(WeatherApi::class.java)


    fun getWeather(receiver: WeatherReceiver) {
        service.getCurrentWeather("Palermo", UNIT_METRIC, "it", API_KEY)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.d("onFailure", "Error: $t")
                    receiver.receive(WeatherResult.Error(t))
                }

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {

                    val success = response.body()
                    if (success != null) {
                        val weatherResponse = response.body()!!
                        receiver.receive(WeatherResult.Success(weatherResponse))
                        Log.d("onResponseBody", "Success: $success")

                    } else {
                        receiver.receive(
                            WeatherResult.Error(
                                Throwable(
                                    response.errorBody().toString()
                                )
                            )
                        )
                        Log.d("onResponseError", "Success: " + response.errorBody().toString())

                    }
                }

            })
    }
}


interface WeatherApi {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("lang") lang: String = "it_it",
        @Query("APPID") app_id: String

    ): Call<WeatherResponse>
}