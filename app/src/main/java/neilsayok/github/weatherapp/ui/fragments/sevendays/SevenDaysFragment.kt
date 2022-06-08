package neilsayok.github.weatherapp.ui.fragments.sevendays

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import neilsayok.github.weatherapp.R
import neilsayok.github.weatherapp.databinding.FragmentSevenDaysBinding
import neilsayok.github.weatherapp.databinding.FragmentTodayBinding


class SevenDaysFragment : Fragment() {

    private lateinit var binding: FragmentSevenDaysBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSevenDaysBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}