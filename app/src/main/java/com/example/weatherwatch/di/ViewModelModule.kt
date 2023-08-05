package com.example.weatherwatch.di

import androidx.lifecycle.ViewModel
import com.example.weatherwatch.presentation.viewModels.FavoritesPlaceViewModel
import com.example.weatherwatch.presentation.viewModels.MainViewModel
import com.example.weatherwatch.presentation.viewModels.SearchPlaceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(SearchPlaceViewModel::class)
    @Binds
    fun bindSearchPlaceViewModel(impl: SearchPlaceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavoritesPlaceViewModel::class)
    @Binds
    fun bindFavoritesPlaceViewModel(impl: FavoritesPlaceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel
}