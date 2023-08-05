package com.example.weatherwatch.di

import android.app.Application
import com.example.weatherwatch.presentation.fragments.FavoritesPlaceFragment
import com.example.weatherwatch.presentation.fragments.MainFragment
import com.example.weatherwatch.presentation.fragments.SearchPlaceFragment
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: SearchPlaceFragment)
    fun inject(fragment: FavoritesPlaceFragment)
    fun inject(fragment: MainFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}