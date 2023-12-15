package com.na0.nayoung_code_interview.network

import androidx.paging.PagingData
import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.util.Constants.IMAGE_API
import com.na0.nayoung_code_interview.util.Constants.SEARCH_API
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET(SEARCH_API)
    suspend fun search(
        @Query("query") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): ImagesResponse

    @GET(IMAGE_API)
    suspend fun get(
        @Query("id") id: String
    ): List<UnsplashResponse>

    @GET(SEARCH_API)
    suspend fun imageList(
        @Query("query") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): ImagesResponse//Flow<PagingData<ImagesResponse>>
}