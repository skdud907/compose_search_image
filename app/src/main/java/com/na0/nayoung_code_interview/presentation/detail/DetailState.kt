package com.na0.nayoung_code_interview.presentation.detail

import com.na0.nayoung_code_interview.model.UnsplashResponse

sealed class DetailState {
    data class GetDetailState(
        val unsplashResponse: UnsplashResponse
    ): DetailState()
}