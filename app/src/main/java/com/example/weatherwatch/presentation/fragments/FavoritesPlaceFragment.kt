package com.example.weatherwatch.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.lifecycle.ViewModelProvider
import com.example.weatherwatch.databinding.FragmentFavoritesPlaceBinding
import com.example.weatherwatch.domain.place.PlaceInfo
import com.example.weatherwatch.presentation.WeatherApp
import com.example.weatherwatch.presentation.adapters.FavoritesPlaceAdapter
import com.example.weatherwatch.presentation.viewModels.FavoritesPlaceViewModel
import com.example.weatherwatch.presentation.viewModels.ViewModelFactory
import javax.inject.Inject


class FavoritesPlaceFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoritesPlaceViewModel::class.java]
    }

    private var _binding: FragmentFavoritesPlaceBinding? = null
    private val binding: FragmentFavoritesPlaceBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoritesPlaceBinding is null")
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
        _binding = FragmentFavoritesPlaceBinding.inflate(inflater, container, false)

        setOnClicks()
        setRecyclerView()

        return binding.root
    }

    private fun setOnClicks() {
        binding.backIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.searchIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .apply {
                    setTransition(TRANSIT_FRAGMENT_FADE)
                }
                .replace((requireView().parent as ViewGroup).id, SearchPlaceFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setRecyclerView() {
        val adapter = FavoritesPlaceAdapter(requireContext())
        adapter.onPlaceClickListener = object : FavoritesPlaceAdapter.OnPlaceClickListener {
            override fun onPlaceClick(placeInfo: PlaceInfo) {

            }

            override fun onDeleteClick(placeInfo: PlaceInfo) {
                viewModel.deletePlace(placeInfo)
                viewModel.getPlaceInfoList()
            }

            override fun onSelectClick(place: PlaceInfo) {
                viewModel.placeSelect(place)
            }
        }
        binding.rvPlacesList.adapter = adapter
        binding.rvPlacesList.itemAnimator = null
        viewModel.placeInfoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getPlaceInfoList()
    }

    companion object {
        fun newInstance(): Fragment = FavoritesPlaceFragment()
    }
}