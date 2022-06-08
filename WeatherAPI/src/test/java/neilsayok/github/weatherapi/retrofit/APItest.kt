package neilsayok.github.weatherapi.retrofit

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class APItest {

    private lateinit var api: WeatherAPI


    @Before
    fun setUp() {
        api = RetrofitBuilder.getRetrofitBuilder().create(WeatherAPI::class.java)
    }

    @Test
    fun `test fetch weather data`(){
        val lat = Random(System.nanoTime()).nextDouble(-90.0,90.0)
        val longitude = Random(System.nanoTime()).nextDouble(-180.0,180.0)

        val res = api.getWeatherData(lat, longitude).execute()
        assertThat(res).isNotNull()

    }





}