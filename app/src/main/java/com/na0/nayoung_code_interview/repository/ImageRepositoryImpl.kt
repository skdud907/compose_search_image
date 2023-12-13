package com.na0.nayoung_code_interview.repository

import com.na0.nayoung_code_interview.model.ImagesResponse
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.network.NetworkService

class ImageRepositoryImpl(
    private val networkService: NetworkService,
): ImageRepository {
    override suspend fun search(query: String?, page: Int, perPage: Int): ImagesResponse {
        return networkService.search(query, page, perPage)
    }

    override suspend fun get(id: String): List<UnsplashResponse> {
        return networkService.get(id)
    }
}