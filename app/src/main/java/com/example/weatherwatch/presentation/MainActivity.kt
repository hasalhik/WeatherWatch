package com.example.weatherwatch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherwatch.R
import com.example.weatherwatch.databinding.ActivityMainBinding
import com.example.weatherwatch.presentation.fragments.MainFragment
import com.example.weatherwatch.presentation.fragments.SearchPlaceFragment


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFragment()


    }

    private fun setFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            //.replace(R.id.fragment_container, SearchPlaceFragment.newInstance())
            .commit()
    }
}