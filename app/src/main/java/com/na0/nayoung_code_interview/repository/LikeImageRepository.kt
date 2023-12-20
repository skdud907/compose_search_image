package com.na0.nayoung_code_interview.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import kotlinx.coroutines.flow.Flow

interface LikeImageRepository {
    suspend fun insert(likeImageEntity: LikeImageEntity)
    suspend fun delete(likeImageEntity: LikeImageEntity)

    suspend fun update(likeImageEntity: LikeImageEntity)

    suspend fun getAllLikedImage(): List<LikeImageEntity>
}