package com.example.weatherwatch.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwatch.databinding.FragmentDayForecastBinding

class DayForecastFragment : Fragment() {

    private var _binding: FragmentDayForecastBinding? = null
    private val binding: FragmentDayForecastBinding
        get() = _binding ?: throw RuntimeException("FragmentDayForecastBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(): Fragment = DayForecastFragment()
    }
}