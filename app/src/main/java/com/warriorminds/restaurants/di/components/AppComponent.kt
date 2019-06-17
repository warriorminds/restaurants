package com.warriorminds.restaurants.di.components

import com.warriorminds.restaurants.RestaurantApp
import com.warriorminds.restaurants.di.modules.AndroidModule
import com.warriorminds.restaurants.di.modules.RestaurantsModule
import com.warriorminds.restaurants.di.modules.ViewModelsModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AndroidModule::class, RestaurantsModule::class, ViewModelsModule::class])
interface AppComponent {
    fun inject(app: RestaurantApp)
}