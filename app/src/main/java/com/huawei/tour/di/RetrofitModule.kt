package com.huawei.tour.di

import com.huawei.tour.repository.remote.TourRemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideTourRemoteApiService(retrofit: Retrofit): TourRemoteApi {
        return retrofit.create(TourRemoteApi::class.java)
    }

    private const val API_BASE_URL = "https://www.travel.taipei/open-api/"
}
