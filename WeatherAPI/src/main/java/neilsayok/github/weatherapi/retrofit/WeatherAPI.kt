package neilsayok.github.weatherapi.retrofit

import neilsayok.github.weatherapi.data.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("onecall")
    fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): Call<Weather>

}