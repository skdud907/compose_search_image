package com.na0.nayoung_code_interview.presentation.bookmark

import com.na0.nayoung_code_interview.model.UnsplashResponse

sealed class BookMarkState {
    data class GetBookMarkState(
        val unsplashResponses: List<UnsplashResponse>
    ): BookMarkState()
}