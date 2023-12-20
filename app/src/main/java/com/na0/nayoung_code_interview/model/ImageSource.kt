package com.na0.nayoung_code_interview.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.na0.nayoung_code_interview.network.NetworkService
import com.na0.nayoung_code_interview.util.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

class ImageDataSource @Inject constructor(
    private val query: String,
    private val networkService: NetworkService
) : PagingSource<Int, UnsplashResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashResponse> {
        val currentPage = params.key ?: 1
        return try {
            val response = networkService.imageList(query = query, page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.results.isEmpty()
            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashResponse>): Int? {
        return state.anchorPosition
    }
}