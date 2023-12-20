package com.na0.nayoung_code_interview.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikeImageEntity::class], version = 1)
abstract class LikeImageDataBase : RoomDatabase() {
    abstract fun likeImageDao(): LikeImageDao
}