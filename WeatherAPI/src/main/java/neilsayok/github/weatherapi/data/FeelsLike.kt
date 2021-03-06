package neilsayok.github.weatherapi.data


import com.google.gson.annotations.SerializedName

data class FeelsLike(
    @SerializedName("day")
    var day: Double? = 0.0,
    @SerializedName("eve")
    var eve: Double? = 0.0,
    @SerializedName("morn")
    var morn: Double? = 0.0,
    @SerializedName("night")
    var night: Double? = 0.0
)