package neilsayok.github.weatherapp.ui.fragments.today

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.FragmentTodayBinding
import neilsayok.github.weatherapp.ui.activity.MainActivity


class TodayFragment : Fragment() {


    private lateinit var binding: FragmentTodayBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodayBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.checkWeatherBtn.setOnClickListener(onClickListener)

        MainActivity.weatherViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            Log.d("Weather", it.toString())

            val currentWeather = it.current

            binding.feelsLikeTv.text = "Feels like ${currentWeather?.feelsLike}"+getString(R.string.degree_c)
            binding.tempTv.text = currentWeather?.temp.toString()
            //binding.weatherDescriptionTv.text = currentWeather?.weather



        })



    }

    val onClickListener = View.OnClickListener{
        when(it.id){
            R.id.check_weather_btn->{
                val lat_text: String = binding.latitudeInp.editText?.text.toString()
                val lon_text: String = binding.longitudeInp.editText?.text.toString()

                if(lat_text.isEmpty() || lat_text.toDouble() < -90.0 || lat_text.toDouble() > 90.0 ){
                    Toast.makeText(requireContext(),"Latitude value is not correct", Toast.LENGTH_LONG).show()
                }else if(lon_text.isEmpty() || lon_text.toDouble() < -180.0 || lon_text.toDouble() > 180.0 ){
                    Toast.makeText(requireContext(),"Longitude value is not correct", Toast.LENGTH_LONG).show()
                }else{
                    //TODO Fetch Data from api
                    MainActivity.weatherViewModel.fetchWeatherData(
                        latitude = lat_text.toDouble(),
                        longitude = lon_text.toDouble()
                    )
                }
            }
        }
    }



}