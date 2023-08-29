package com.example.weatherwatch.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherwatch.R
import com.example.weatherwatch.databinding.FragmentSettingsBinding
import com.example.weatherwatch.domain.SettingConstants
import com.example.weatherwatch.presentation.WeatherApp


class SettingsFragment : Fragment() {

    lateinit var prefs: SharedPreferences
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding is null")
    private val component by lazy {
        (requireActivity().application as WeatherApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        prefs = requireContext().getSharedPreferences(
            SettingConstants.APP_SETTINGS_NAME,
            Context.MODE_PRIVATE
        )
        setPrefChangeListener()
        setButtonsClickListeners()
        Log.d("SettingsFragment", "prefs ${prefs.all}")
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    private fun setButtonsClickListeners() {
        binding.celsiusTextView.setOnClickListener {
            prefs.edit().putBoolean(SettingConstants.IS_TEMPERATURE_IN_FAHRENHEIT, false).apply()
        }
        binding.fahrenheitTextView.setOnClickListener {
            prefs.edit().putBoolean(SettingConstants.IS_TEMPERATURE_IN_FAHRENHEIT, true).apply()
        }
        binding.mSTextView.setOnClickListener {
            prefs.edit().putBoolean(SettingConstants.IS_WIND_IN_KM_PER_HOUR, false).apply()
        }
        binding.kmHTextView.setOnClickListener {
            prefs.edit().putBoolean(SettingConstants.IS_WIND_IN_KM_PER_HOUR, true).apply()
        }
        binding.mmHgTextView.setOnClickListener {
            prefs.edit().putBoolean(SettingConstants.IS_PRESSURE_IN_HPA, false).apply()
        }
        binding.hPaTextView.setOnClickListener {
            prefs.edit().putBoolean(SettingConstants.IS_PRESSURE_IN_HPA, true).apply()
        }

    }

    private fun setPrefChangeListener() {
        checkPref(prefs)
        prefs.registerOnSharedPreferenceChangeListener { sharedPreferences, p1 ->
            run {
                Log.d("SettingsFragment", "chengPref")
                checkPref(
                    sharedPreferences
                )
            }
        }
    }

    private fun checkPref(sharedPreferences: SharedPreferences?) {
        if (sharedPreferences != null) {
            if (sharedPreferences.getBoolean(
                    SettingConstants.IS_TEMPERATURE_IN_FAHRENHEIT,
                    false
                )
            ) {
                binding.celsiusTextView.setBackgroundResource(R.color.light_grey)
                binding.fahrenheitTextView.setBackgroundResource(R.color.orange)
            } else {
                binding.celsiusTextView.setBackgroundResource(R.color.orange)
                binding.fahrenheitTextView.setBackgroundResource(R.color.light_grey)
            }

            if (sharedPreferences.getBoolean(
                    SettingConstants.IS_WIND_IN_KM_PER_HOUR,
                    false
                )
            ) {
                binding.mSTextView.setBackgroundResource(R.color.light_grey)
                binding.kmHTextView.setBackgroundResource(R.color.orange)
            } else {
                binding.mSTextView.setBackgroundResource(R.color.orange)
                binding.kmHTextView.setBackgroundResource(R.color.light_grey)
            }

            if (sharedPreferences.getBoolean(SettingConstants.IS_PRESSURE_IN_HPA, false)) {
                binding.mmHgTextView.setBackgroundResource(R.color.light_grey)
                binding.hPaTextView.setBackgroundResource(R.color.orange)
            } else {
                binding.mmHgTextView.setBackgroundResource(R.color.orange)
                binding.hPaTextView.setBackgroundResource(R.color.light_grey)
            }


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(): Fragment = SettingsFragment()
    }
}