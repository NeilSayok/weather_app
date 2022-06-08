package neilsayok.github.weatherapp.ui.fragments.sevendays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import neilsayok.github.weatherapp.databinding.FragmentSevenDaysBinding
import neilsayok.github.weatherapp.ui.activity.MainActivity
import neilsayok.github.weatherapp.ui.adapter.SevenDaysRVAdapter


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

        val l = MainActivity.weatherViewModel.weatherData.value

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            layoutManager.orientation
        )

        binding.sevendaysRv.layoutManager = layoutManager
        binding.sevendaysRv.addItemDecoration(dividerItemDecoration);

        if(l!=null){
            binding.sevendaysRv.adapter = SevenDaysRVAdapter(l.daily)
        }


        MainActivity.weatherViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            binding.sevendaysRv.adapter = SevenDaysRVAdapter(it.daily)
        })
    }

}