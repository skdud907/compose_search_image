package com.na0.nayoung_code_interview.model

data class ImagesResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<UnsplashResponse>
)

