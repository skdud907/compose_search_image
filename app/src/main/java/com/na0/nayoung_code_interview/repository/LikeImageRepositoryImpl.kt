package com.na0.nayoung_code_interview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.na0.nayoung_code_interview.model.db.LikeImageDao
import com.na0.nayoung_code_interview.model.db.LikeImageDataBase
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import com.na0.nayoung_code_interview.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    override suspend fun update(likeImageEntity: LikeImageEntity) {
        likeImageDao.update(likeImageEntity)
    }

    override suspend fun getAllLikedImage(): List<LikeImageEntity> {
        return withContext(Dispatchers.IO) {
            likeImageDao.getAll()
        }
    }
}