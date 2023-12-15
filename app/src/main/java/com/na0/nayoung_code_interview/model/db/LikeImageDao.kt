package com.na0.nayoung_code_interview.model.db

import androidx.room.*

@Dao
interface LikeImageDao {
    @Query("SELECT * FROM like_image_list")
    fun getAll(): List<LikeImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg likeImages: LikeImageEntity)

    @Delete
    suspend fun delete(vararg likeImages: LikeImageEntity)

    @Query("DELETE FROM like_image_list")
    suspend fun clear()
}