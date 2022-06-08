package neilsayok.github.weatherapi.data


import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("icon")
    var icon: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("main")
    var main: String? = ""
)