package com.na0.nayoung_code_interview.di

import android.content.Context
import androidx.room.Room
import com.na0.nayoung_code_interview.model.db.LikeImageDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            LikeImageDataBase::class.java,
            "images_db"
        )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideLikeImageDao(appDatabase: LikeImageDataBase) = appDatabase.likeImageDao()
}