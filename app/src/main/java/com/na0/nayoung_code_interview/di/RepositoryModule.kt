package com.na0.nayoung_code_interview.di

import com.na0.nayoung_code_interview.network.NetworkService
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.repository.ImageRepositoryImpl
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
    fun provideImageRepository(
        networkService: NetworkService,
    ): ImageRepository {
        return ImageRepositoryImpl(
            networkService = networkService,
        )
    }
}