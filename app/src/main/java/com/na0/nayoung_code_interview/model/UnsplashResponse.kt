package com.na0.nayoung_code_interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashResponse(
    val id: String,
    val description: String?,
    val width: Int,
    val height: Int,
    val created_at: String,
    val likes: Int,
    val urls: Urls,
    val user: Users,
): Parcelable {
    @Parcelize
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ): Parcelable

    @Parcelize
    data class Users(
        val name: String,
        val profile_image: ProfileImages,
    ): Parcelable {
        @Parcelize
        data class ProfileImages(
            val small: String,
            val medium: String,
            val large: String,
        ): Parcelable
    }
}
