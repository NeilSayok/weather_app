package neilsayok.github.weatherapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.ActivityMainBinding
import neilsayok.github.weatherapp.ui.adapter.MainViewPagerAdapter
import neilsayok.github.weatherapp.ui.fragments.sevendays.SevenDaysFragment
import neilsayok.github.weatherapp.ui.fragments.today.TodayFragment
import neilsayok.github.weatherapp.ui.fragments.tommorow.TommorowFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fragmentList = listOf(
            TodayFragment(),
            TommorowFragment(),
            SevenDaysFragment()
        )

        binding.mainViewpaer.adapter = MainViewPagerAdapter(this,fragmentList)

        TabLayoutMediator(binding.mainTabLayout,binding.mainViewpaer){ tab, position ->
            when(position){
                0->tab.text = "Today"
                1->tab.text = "Tommorow"
                2->tab.text = "Next 7 Days"
            }
        }.attach()





    }
}