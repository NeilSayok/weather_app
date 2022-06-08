package neilsayok.github.weatherapi.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitBuilder {


    companion object{
        private const val baseUrl = "https://api.openweathermap.org/data/2.5/"

        val logging = HttpLoggingInterceptor()


        val client = OkHttpClient.Builder()
            .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
            .build()

        private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", "364ba416ff09e1a521cac00302d081bd").build()
            println(newUrl)
            request.url(newUrl)
            return chain.proceed(request.build())
        }

        @Volatile
        private var retrofit: Retrofit? = null

        @Synchronized
        fun getRetrofitBuilder():Retrofit{
            if (retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }







}
