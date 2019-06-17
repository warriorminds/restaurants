package com.warriorminds.restaurants.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.warriorminds.restaurants.viewmodels.DetailsViewModel
import com.warriorminds.restaurants.viewmodels.MapViewModel
import com.warriorminds.restaurants.viewmodels.ViewModelFactory
import com.warriorminds.restaurants.viewmodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MapViewModel::class)
    abstract fun providesMapViewModel(mapViewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(DetailsViewModel::class)
    abstract fun providesDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}