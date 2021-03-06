package com.poc.jsonplaceholderapisample.di

import com.poc.jsonplaceholderapisample.gateway.RetrofitService
import com.poc.jsonplaceholderapisample.model.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(retrofit: RetrofitService): Repository {
        return Repository(retrofit)
    }

}