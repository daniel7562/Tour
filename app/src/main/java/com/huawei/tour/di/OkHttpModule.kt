package com.huawei.tour.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
object OkHttpModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_S, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_S, TimeUnit.SECONDS)
            .build()
    }

    private const val TIMEOUT_S = 30L
}