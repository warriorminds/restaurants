package com.warriorminds.restaurants.di.modules

import com.warriorminds.restaurants.ui.activities.MainActivity
import com.warriorminds.restaurants.ui.fragments.DetailsFragment
import com.warriorminds.restaurants.ui.fragments.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun mapFragment(): MapFragment

    @ContributesAndroidInjector
    abstract fun detailsFragment(): DetailsFragment
}