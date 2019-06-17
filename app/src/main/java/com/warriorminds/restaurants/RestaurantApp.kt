package com.warriorminds.restaurants

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.warriorminds.restaurants.di.components.DaggerAppComponent
import com.warriorminds.restaurants.di.modules.RestaurantsModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class RestaurantApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.builder()
            .restaurantsModule(RestaurantsModule(this))
            .build()
        appComponent.inject(this)
    }

    override fun activityInjector() = dispatchingActivityInjector

    override fun supportFragmentInjector() = dispatchingFragmentInjector
}