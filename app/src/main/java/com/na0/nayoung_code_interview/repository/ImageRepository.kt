package com.na0.nayoung_code_interview.repository

import androidx.paging.PagingData
import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.UnsplashResponse
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun search(query: String?, page: Int, perPage: Int): ImagesResponse
    suspend fun get(id: String): List<UnsplashResponse>
    fun imageList(query: String): Flow<PagingData<UnsplashResponse>>
}