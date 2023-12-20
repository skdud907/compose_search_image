package com.na0.nayoung_code_interview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.na0.nayoung_code_interview.model.ImageDataSource
import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.network.NetworkService
import com.na0.nayoung_code_interview.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class ImageRepositoryImpl(
    private val networkService: NetworkService,
): ImageRepository {
    override suspend fun search(query: String?, page: Int, perPage: Int): ImagesResponse {
        return networkService.search(query, page, perPage)
    }

    override suspend fun get(id: String): List<UnsplashResponse> {
        return networkService.get(id)
    }

    override fun imageList(query: String): Flow<PagingData<UnsplashResponse>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                ImageDataSource(networkService = networkService, query = query)
            }
        ).flow
    }
}