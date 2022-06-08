package neilsayok.github.weatherapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import neilsayok.github.weatherapi.data.Daily
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.RecyclerItemSevenDaysBinding
import neilsayok.github.weatherapp.ui.adapter.viewholder.SevenDaysRVViewHolder
import java.text.SimpleDateFormat
import java.util.*

class SevenDaysRVAdapter(val dailyList: List<Daily>?) :
    RecyclerView.Adapter<SevenDaysRVViewHolder>() {

    private lateinit var mCtx: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SevenDaysRVViewHolder {
        val binding = RecyclerItemSevenDaysBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        mCtx = parent.context
        return SevenDaysRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SevenDaysRVViewHolder, position: Int) {
        with(holder) {
            with(dailyList!![position]) {

                Picasso.get()
                    .load("https://openweathermap.org/img/wn/${weatherDescription?.get(0)?.icon}@2x.png")
                    .placeholder(R.drawable.ic_baseline_download_24)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(binding.rvWeatherIcon)


                SimpleDateFormat("E ,M d").format(Date(1654686000))
                binding.dateTv.text =
                    SimpleDateFormat("EEEEEEEEE,d MMM", Locale.getDefault()).format(
                        Date((dt ?: 0) * 1000L)
                    )
                val wd = weatherDescription?.get(0)?.description ?: ""
                binding.weatherDescTv.text = wd.split(' ')
                    .joinToString(separator = " ") { word -> word.replaceFirstChar { text -> text.uppercase() } }

                binding.minTempTv.text = temp?.min.toString() + mCtx.getString(R.string.degree_c)
                binding.maxTempTv.text = temp?.max.toString() + mCtx.getString(R.string.degree_c)

                binding.windSpeedTv.text = windSpeed.toString() + " km/h"
                binding.humidityPercentTv.text = humidity.toString() + "%"
                binding.sunTimeTv.text =
                    "${
                        SimpleDateFormat(
                            "K:m a",
                            Locale.getDefault()
                        ).format(Date((sunrise ?: 0) * 1000L))
                    },${
                        SimpleDateFormat(
                            "K:m a",
                            Locale.getDefault()
                        ).format(Date((sunset ?: 0) * 1000L))
                    }"

            }
        }
    }

    override fun getItemCount(): Int = dailyList?.size ?: 0
}