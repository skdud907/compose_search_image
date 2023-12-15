package com.na0.nayoung_code_interview.di

import com.na0.nayoung_code_interview.model.db.LikeImageDao
import com.na0.nayoung_code_interview.network.NetworkService
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.repository.ImageRepositoryImpl
import com.na0.nayoung_code_interview.repository.LikeImageRepository
import com.na0.nayoung_code_interview.repository.LikeImageRepositoryImpl
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

    @Singleton
    @Provides
    fun provideLikeImageRepository(
        likeImageDao: LikeImageDao,
    ): LikeImageRepository {
        return LikeImageRepositoryImpl(
            likeImageDao = likeImageDao,
        )
    }
}