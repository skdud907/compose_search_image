package com.na0.nayoung_code_interview.presentation.detail

import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity

sealed class DetailState {
    data class GetDetailState(
        val unsplashResponse: UnsplashResponse
    ): DetailState()

    data class GetLikedDetailState(
        val likesImages: LikeImageEntity
    ): DetailState()

    data class GetScreenType(
        val screenType: Int
    ): DetailState()
}