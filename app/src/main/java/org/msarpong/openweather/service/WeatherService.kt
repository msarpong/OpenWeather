package org.msarpong.openweather.service


import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.msarpong.openweather.datamapping.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "eb490b1f6e000eda7b37d3decbb81ff6"
private const val UNIT_METRIC = "metric"
private const val UNIT_IMPERIAL = "imperial"

sealed class WeatherResult {
    data class Error(val error: Throwable) : WeatherResult()
    data class Success(val weatherList: WeatherResponse) : WeatherResult()
}

interface WeatherReceiver {
    fun receive(result: WeatherResult)
}

class WeatherService {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

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