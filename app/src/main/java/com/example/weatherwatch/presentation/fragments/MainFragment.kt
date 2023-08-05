package com.example.weatherwatch.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherwatch.R
import com.example.weatherwatch.databinding.FragmentMainBinding
import com.example.weatherwatch.presentation.WeatherApp
import com.example.weatherwatch.presentation.adapters.ForecastAdapter
import com.example.weatherwatch.presentation.viewModels.MainViewModel
import com.example.weatherwatch.presentation.viewModels.ViewModelFactory
import javax.inject.Inject


class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private val component by lazy {
        (requireActivity().application as WeatherApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    @SuppressLint("DiscouragedApi", "SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.dataConstrainLayout.visibility = View.INVISIBLE
        viewModel.loadData()
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            if (it.weather.size == 1) {
                Log.d("MainFragment", "currentWeatherList: ${it.toString()}")
                binding.dataConstrainLayout.visibility = View.VISIBLE

                binding.weatherIcon.setImageResource(
                    resources.getIdentifier(
                        "@drawable/ic_${it.weather[0].icon}", null, context?.packageName
                    )
                )
                binding.bigPlaceName.text = it.name
                binding.smallPlaceName.text = it.dt
                binding.degree.text = it.main?.temp
                binding.description.text = it.weather[0].description
                binding.filesLikeValue.text = it.main?.feelsLike
                binding.windValue.text = it.wind.speed + it.wind.deg
                binding.pressureValue.text = it.main?.pressure
                binding.humidValue.text = it.main?.humidity
            } else binding.bigPlaceName.text = (resources.getString(R.string.choose_place))
        }
        val adapter = ForecastAdapter(requireContext())
        binding.rvForecast.adapter = adapter
        binding.rvForecast.itemAnimator = null
        viewModel.forecast.observe(viewLifecycleOwner) {
            Log.d("MainFragment", "forecast: ${it.toString()}")
            adapter.submitList(it.listForecast)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoritesPlaceIcon.setOnClickListener {
            launchFragmentFavoritesPlaceFragment()
        }
        binding.settingsIcon.setOnClickListener {
            launchFragmentSettingsFragment()
        }
    }

    private fun launchFragmentFavoritesPlaceFragment() {
        parentFragmentManager.beginTransaction()
            .apply {
                setCustomAnimations(
                    R.animator.slide_left_to_center, R.animator.slide_center_to_right,
                    R.animator.slide_right_to_center, R.animator.slide_center_to_left
                )
            }
            .replace((requireView().parent as ViewGroup).id, FavoritesPlaceFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun launchFragmentSettingsFragment() {
        parentFragmentManager.beginTransaction()
            .apply {
                setCustomAnimations(
                    R.animator.slide_right_to_center, R.animator.slide_center_to_left,
                    R.animator.slide_left_to_center, R.animator.slide_center_to_right,
                )
            }
            .replace((requireView().parent as ViewGroup).id, SettingsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


    companion object {
        fun newInstance(): Fragment = MainFragment()
    }
}