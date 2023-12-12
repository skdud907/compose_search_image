package com.na0.nayoung_code_interview.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.na0.nayoung_code_interview.BuildConfig
import com.na0.nayoung_code_interview.network.NetworkService
import com.na0.nayoung_code_interview.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideNetworkService(): NetworkService {
        val okhttp = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(RequestInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()

        return retrofit.create(NetworkService::class.java)
    }

    class RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            val addedUrl = originalRequest.url.newBuilder().addQueryParameter(
                "client_id",
                BuildConfig.UNSPLASH_ACCESS_KEY
            ).build()

            val finalRequest = originalRequest.newBuilder()
                .url(addedUrl)
                .method(originalRequest.method, originalRequest.body)
                .build()

            val response = chain.proceed(finalRequest)

            if (response.code != 200) {
                Log.e("RequestInterceptor", "repose code = ${response.code}")
            }

            return response
        }
    }

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL
//
//    @Singleton
//    @Provides
//    fun provideNetworkService(): NetworkService {
//        return Retrofit.Builder()
//            .baseUrl("https://food2fork.ca/api/recipe/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(NetworkService::class.java)
//    }
}