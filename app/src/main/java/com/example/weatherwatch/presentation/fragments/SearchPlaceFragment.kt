package com.example.weatherwatch.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherwatch.databinding.FragmentSearchPlaceBinding
import com.example.weatherwatch.domain.place.PlaceInfo
import com.example.weatherwatch.presentation.WeatherApp
import com.example.weatherwatch.presentation.adapters.SearchPlaceAdapter
import com.example.weatherwatch.presentation.viewModels.SearchPlaceViewModel
import com.example.weatherwatch.presentation.viewModels.ViewModelFactory
import javax.inject.Inject


class SearchPlaceFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchPlaceViewModel::class.java]
    }

    private var _binding: FragmentSearchPlaceBinding? = null
    private val binding: FragmentSearchPlaceBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchPlaceBinding is null")
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
        _binding = FragmentSearchPlaceBinding.inflate(inflater, container, false)
        setListeners()
        val adapter = SearchPlaceAdapter(requireContext())
        adapter.onPlaceClickListener = object : SearchPlaceAdapter.OnPlaceClickListener {
            override fun onPlaceClick(placeInfo: PlaceInfo) {
                Log.d("SearchPlaceFragment", "Start insert ${placeInfo.name}")
                viewModel.insertPlaceToDb(placeInfo)
                parentFragmentManager.popBackStack()

            }
        }
        binding.rvResult.adapter = adapter
        binding.rvResult.itemAnimator = null
        viewModel.placeInfoList.observe(viewLifecycleOwner) {
            Log.d(
                "SearchPlaceFragment layoutManager: ",
                "${binding.rvResult.layoutManager.toString()}"
            )
            adapter.submitList(it)
        }
        return binding.root
    }


    private fun setListeners() {
        binding.backIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.clearIcon.setOnClickListener {
            binding.editTextSearch.text.clear()

        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                setClearIconVisibility()
                viewModel.getPlaceInfoList(p0)
            }

        })
    }

    private fun setClearIconVisibility() {
        if (binding.editTextSearch.text.isNullOrEmpty())
            binding.clearIcon.visibility = View.GONE
        else
            binding.clearIcon.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): Fragment = SearchPlaceFragment()
    }
}


