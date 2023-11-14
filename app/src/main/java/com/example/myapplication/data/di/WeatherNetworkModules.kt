package com.example.myapplication.data.di

import com.example.myapplication.data.network.WeatherAPI
import java.util.concurrent.TimeUnit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherNetworkModules {
    @Provides
    @Singleton
    fun providerGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }


}