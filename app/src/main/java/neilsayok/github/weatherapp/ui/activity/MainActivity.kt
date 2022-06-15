package neilsayok.github.weatherapp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import neilsayok.github.weatherapi.viewmodel.WeatherViewModel
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.ActivityMainBinding
import neilsayok.github.weatherapp.ui.adapter.MainViewPagerAdapter
import neilsayok.github.weatherapp.ui.fragments.sevendays.SevenDaysFragment
import neilsayok.github.weatherapp.ui.fragments.today.TodayFragment
import neilsayok.github.weatherapp.ui.fragments.tommorow.TommorowFragment
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    private val RC_LOCATION_PERM = 124
    private val TAG = "Permission Request"

    companion object{
        lateinit var weatherViewModel: WeatherViewModel
        lateinit var binding: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationTask()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]


        val fragmentList = listOf(
            TodayFragment(),
            TommorowFragment(),
            SevenDaysFragment(),
            //MapsFragment()
        )

        binding.mainViewpaer.adapter = MainViewPagerAdapter(this,fragmentList)

        TabLayoutMediator(binding.mainTabLayout,binding.mainViewpaer){ tab, position ->
            when(position){
                0->tab.text = "Today"
                1->tab.text = "Next 48 Hrs"
                2->tab.text = "Next 7 Days"
            }
        }.attach()





    }



    private fun hasLocationPermissions():Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun locationTask() {
        if (hasLocationPermissions())
        {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Location things", Toast.LENGTH_LONG).show()
        }
        else
        {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    override fun onRequestPermissionsResult(requestCode:Int,
                                            permissions:Array<String>,
                                            grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
        {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                this,
                getString(R.string.returned_from_app_settings_to_activity,
                    if (hasLocationPermissions()) yes else no),
                Toast.LENGTH_LONG)
                .show()
        }
    }
    override fun onRationaleAccepted(requestCode:Int) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode)
    }
    override fun onRationaleDenied(requestCode:Int) {
        Log.d(TAG, "onRationaleDenied:" + requestCode)
    }
}