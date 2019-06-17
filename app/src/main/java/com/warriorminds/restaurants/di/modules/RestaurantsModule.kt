package com.warriorminds.restaurants.di.modules

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.warriorminds.restaurants.maps.GoogleMapProvider
import com.warriorminds.restaurants.maps.LocationProvider
import com.warriorminds.restaurants.maps.LocationProviderImpl
import com.warriorminds.restaurants.maps.MapProvider
import com.warriorminds.restaurants.network.RestaurantsService
import com.warriorminds.restaurants.repositories.DetailsRepository
import com.warriorminds.restaurants.repositories.DetailsRepositoryImpl
import com.warriorminds.restaurants.repositories.MapRepository
import com.warriorminds.restaurants.repositories.MapRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RestaurantsModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesContext(): Context = context

    @Singleton
    @Provides
    fun providesOkHttp() = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            var request = chain.request()
            val url =
                request.url().newBuilder()
                    .addQueryParameter("client_id", "PUJTFA2SWRFAMCMBELPCDAP3IXCZ2TZH3HZJSSNCEFMA1TKB")
                    .addQueryParameter("client_secret", "BB2KEOTTIAMHAI1WQFEX3FAFGQEFJIIVMZRZHHJY1WTG2RBT")
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
        .cache(Cache(File(context.cacheDir, "http-cache"), 25 * 1024 * 1024))
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .baseUrl("https://api.foursquare.com/")
        .build()

    @Singleton
    @Provides
    fun provideRestaurantService(retrofit: Retrofit): RestaurantsService = retrofit.create(RestaurantsService::class.java)

    @Singleton
    @Provides
    fun provideMapProvider(googleMapProvider: GoogleMapProvider): MapProvider = googleMapProvider

    @Singleton
    @Provides
    fun providesMapRepository(mapRepository: MapRepositoryImpl): MapRepository = mapRepository

    @Singleton
    @Provides
    fun providesDetailsRepository(detailsRepository: DetailsRepositoryImpl): DetailsRepository = detailsRepository

    @Singleton
    @Provides
    fun providesLocationProvider(locationProvider: LocationProviderImpl): LocationProvider = locationProvider
}