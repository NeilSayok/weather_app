package neilsayok.github.weatherapp.ui.fragments.today


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.FragmentTodayBinding
import neilsayok.github.weatherapp.ui.activity.MainActivity
import java.text.SimpleDateFormat
import java.util.*


class TodayFragment : Fragment() {


    private lateinit var binding: FragmentTodayBinding
    private lateinit var gMap : GoogleMap
    private lateinit var todayViemodel: TodayViewModel
    private var location: Location? = null
    private lateinit var locationManager: LocationManager
    private var marker: Marker? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodayBinding.inflate(inflater, container, false)


        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(onMapReadyCallback)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todayViemodel = ViewModelProvider(this)[TodayViewModel::class.java]
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if(location!=null){
                todayViemodel.setLocation(location!!)

            }
        }



        binding.checkWeatherBtn.setOnClickListener(onClickListener)
        binding.stopPageScrollBtn.setOnClickListener(onClickListener)

        MainActivity.weatherViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            Log.d("Weather", it.toString())
            toggleProgress(showProgress = false,showCard = false)

            val currentWeather = it.current

            Picasso.get()
                .load("https://openweathermap.org/img/wn/${currentWeather?.weather?.get(0)?.icon}@2x.png")
                .placeholder(R.drawable.ic_baseline_download_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(binding.weatherIcon)

            binding.feelsLikeTv.text =
                "Feels like ${currentWeather?.feelsLike}" + getString(R.string.degree_c)
            binding.tempTv.text = currentWeather?.temp.toString()
            val wd = currentWeather?.weather?.get(0)?.description ?: ""
            binding.weatherDescriptionTv.text = wd.split(' ')
                .joinToString(separator = " ") { word -> word.replaceFirstChar { text -> text.uppercase() } }

            binding.degCTv.visibility = View.VISIBLE

            binding.humidityText.text = currentWeather?.humidity.toString() + "%"
            binding.windspeedTv.text = currentWeather?.windSpeed.toString() + " km/h"

            binding.dateText.text =
                SimpleDateFormat("EEEEEEEEE,d MMM K:m a", Locale.getDefault()).format(
                    Date((currentWeather?.dt ?: 0) * 1000L)
                )



        }
        )

        todayViemodel.loc.observe(viewLifecycleOwner, Observer {
            binding.latitudeInp.editText!!.setText(it.latitude.toString())
            binding.longitudeInp.editText!!.setText(it.longitude.toString())
            if (marker!=null){
                marker!!.position = LatLng(it.latitude,it.longitude)
            }

        })




    }





    val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.check_weather_btn -> {
                val lat_text: String = binding.latitudeInp.editText?.text.toString()
                val lon_text: String = binding.longitudeInp.editText?.text.toString()

                if (lat_text.isEmpty() || lat_text.toDouble() < -90.0 || lat_text.toDouble() > 90.0) {
                    Toast.makeText(
                        requireContext(),
                        "Latitude value is not correct",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (lon_text.isEmpty() || lon_text.toDouble() < -180.0 || lon_text.toDouble() > 180.0) {
                    Toast.makeText(
                        requireContext(),
                        "Longitude value is not correct",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    MainActivity.weatherViewModel.fetchWeatherData(
                        latitude = lat_text.toDouble(),
                        longitude = lon_text.toDouble()
                    )
                    val loc = Location("")
                    loc.longitude = lon_text.toDouble()
                    loc.latitude = lat_text.toDouble()
                    todayViemodel.setLocation(loc)
                    toggleProgress(showProgress = true,showCard = true)
                }
            }
            R.id.stop_page_scroll_btn->{
                MainActivity.binding.mainViewpaer.isUserInputEnabled = !MainActivity.binding.mainViewpaer.isUserInputEnabled
                if(MainActivity.binding.mainViewpaer.isUserInputEnabled){
                    (binding.stopPageScrollBtn as MaterialButton).icon = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_baseline_push_pin_24)
                }else{
                    (binding.stopPageScrollBtn as MaterialButton).icon = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_round_push_pin_angled)

                }
            }
        }
    }

    fun toggleProgress(showProgress: Boolean = true,showCard:Boolean = true){
        if (showProgress){
            binding.loadingProgress.visibility = View.VISIBLE
        }else{
            binding.loadingProgress.visibility = View.GONE
        }

        if (showCard){
            binding.loadingCard.visibility = View.VISIBLE
        }else{
            binding.loadingCard.visibility = View.GONE
        }
    }

    val onMapReadyCallback = OnMapReadyCallback { googleMap ->
        gMap = googleMap;
        gMap.uiSettings.isMyLocationButtonEnabled = false;

        val myLoc =LatLng((location?.latitude ?: 0) as Double,
            (location?.longitude ?: 0) as Double
        )
        marker = gMap.addMarker(
            MarkerOptions()
                .position(myLoc)

                .draggable(true)
        )
        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragStart(p0: Marker) {

            }

            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                val loc = Location("")
                loc.longitude = p0.position.longitude
                loc.latitude = p0.position.latitude
                todayViemodel.setLocation(loc)
            }
        })

        gMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc))
    }





    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }




}