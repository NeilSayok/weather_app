package neilsayok.github.weatherapi.data


import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("clouds")
    var clouds: Int? = 0,
    @SerializedName("dew_point")
    var dewPoint: Double? = 0.0,
    @SerializedName("dt")
    var dt: Int? = 0,
    @SerializedName("feels_like")
    var feelsLike: Double? = 0.0,
    @SerializedName("humidity")
    var humidity: Int? = 0,
    @SerializedName("pop")
    var pop: Double? = 0.0,
    @SerializedName("pressure")
    var pressure: Int? = 0,
    @SerializedName("temp")
    var temp: Double? = 0.0,
    @SerializedName("uvi")
    var uvi: Double? = 0.0,
    @SerializedName("visibility")
    var visibility: Int? = 0,
    @SerializedName("weather")
    var weatherDescription: List<WeatherDescription>? = listOf(),
    @SerializedName("wind_deg")
    var windDeg: Int? = 0,
    @SerializedName("wind_gust")
    var windGust: Double? = 0.0,
    @SerializedName("wind_speed")
    var windSpeed: Double? = 0.0
)