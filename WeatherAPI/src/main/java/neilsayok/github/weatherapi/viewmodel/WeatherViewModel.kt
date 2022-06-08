package neilsayok.github.weatherapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neilsayok.github.weatherapi.data.Weather
import neilsayok.github.weatherapi.retrofit.RetrofitBuilder
import neilsayok.github.weatherapi.retrofit.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel: ViewModel() {

    val weatherData: MutableLiveData<Weather> = MutableLiveData()
    companion object{
        private val api: WeatherAPI = RetrofitBuilder.getRetrofitBuilder().create(WeatherAPI::class.java)

    }

    fun fetchWeatherData(latitude: Double = 0.0, longitude: Double = 0.0){
        viewModelScope.launch{
            api.getWeatherData(latitude,longitude).enqueue(object: Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    try {
                        if (response.isSuccessful){
                            weatherData.value = response.body()
                        }else{
                            Log.d("Resonse", response.message())
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                }
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }


}