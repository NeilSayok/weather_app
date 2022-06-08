package neilsayok.github.weatherapi.data


import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("day")
    var day: Double? = 0.0,
    @SerializedName("eve")
    var eve: Double? = 0.0,
    @SerializedName("max")
    var max: Double? = 0.0,
    @SerializedName("min")
    var min: Double? = 0.0,
    @SerializedName("morn")
    var morn: Double? = 0.0,
    @SerializedName("night")
    var night: Double? = 0.0
)