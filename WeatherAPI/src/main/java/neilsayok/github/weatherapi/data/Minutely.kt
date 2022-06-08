package neilsayok.github.weatherapi.data


import com.google.gson.annotations.SerializedName

data class Minutely(
    @SerializedName("dt")
    var dt: Long? = 0,
    @SerializedName("precipitation")
    var precipitation: Int? = 0
)