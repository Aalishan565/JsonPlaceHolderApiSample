package com.poc.jsonplaceholderapisample.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.poc.jsonplaceholderapisample.gateway.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()

    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, url: String): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return "https://jsonplaceholder.typicode.com/"

    }

    @Singleton
    @Provides
    fun provideRetrofitApi(retrofitBuilder: Retrofit.Builder): RetrofitService {
        return retrofitBuilder.build().create(RetrofitService::class.java)

    }
}