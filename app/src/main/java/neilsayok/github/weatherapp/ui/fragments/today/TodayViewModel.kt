package neilsayok.github.weatherapp.ui.fragments.today

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodayViewModel: ViewModel() {

    val loc = MutableLiveData<Location>()



    fun setLocation(location:Location){
        loc.value = location
    }



}