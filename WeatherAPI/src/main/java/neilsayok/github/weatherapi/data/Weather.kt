package neilsayok.github.weatherapi.data


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current")
    var current: Current? = Current(),
    @SerializedName("daily")
    var daily: List<Daily>? = listOf(),
    @SerializedName("hourly")
    var hourly: List<Hourly>? = listOf(),
    @SerializedName("lat")
    var lat: Double? = 0.0,
    @SerializedName("lon")
    var lon: Double? = 0.0,
    @SerializedName("minutely")
    var minutely: List<Minutely>? = listOf(),
    @SerializedName("timezone")
    var timezone: String? = "",
    @SerializedName("timezone_offset")
    var timezoneOffset: Int? = 0
)