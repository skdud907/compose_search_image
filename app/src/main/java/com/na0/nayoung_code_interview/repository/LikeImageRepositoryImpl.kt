package com.na0.nayoung_code_interview.repository

import com.na0.nayoung_code_interview.model.db.LikeImageDao
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LikeImageRepositoryImpl(
    private val likeImageDao: LikeImageDao,
): LikeImageRepository {
    override suspend fun insert(likeImageEntity: LikeImageEntity) {
        likeImageDao.insert(likeImageEntity)
    }

    override suspend fun delete(likeImageEntity: LikeImageEntity) {
        likeImageDao.delete(likeImageEntity)
    }

    override suspend fun getAllLikedImage(): List<LikeImageEntity> {
        return withContext(Dispatchers.IO) {
            likeImageDao.getAll()
        }
    }
}