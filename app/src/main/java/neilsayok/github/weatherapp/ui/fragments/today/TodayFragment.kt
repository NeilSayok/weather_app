package neilsayok.github.weatherapp.ui.fragments.today

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.FragmentTodayBinding


class TodayFragment : Fragment() {


    private lateinit var binding: FragmentTodayBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTodayBinding.inflate(inflater,container,false)
        return binding.root
    }

}