package com.na0.nayoung_code_interview.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "like_image_list")
@Parcelize
data class LikeImageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "likes") val likes: Boolean,
    @ColumnInfo(name = "urls") val urls: String,
    @ColumnInfo(name = "user") val user: String,
): Parcelable