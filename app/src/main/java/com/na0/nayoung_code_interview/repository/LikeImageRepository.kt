package com.na0.nayoung_code_interview.repository

import androidx.lifecycle.LiveData
import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity

interface LikeImageRepository {
    suspend fun insert(likeImageEntity: LikeImageEntity)
    suspend fun delete(likeImageEntity: LikeImageEntity)

    suspend fun getAllLikedImage(): List<LikeImageEntity>
}