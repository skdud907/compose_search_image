package com.na0.nayoung_code_interview.repository

import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.UnsplashResponse

interface ImageRepository {

    suspend fun search(query: String?, page: Int, perPage: Int): ImagesResponse

    suspend fun get(id: String): UnsplashResponse

}