package com.na0.nayoung_code_interview.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.na0.nayoung_code_interview.model.UnsplashResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.na0.nayoung_code_interview.repository.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ImageRepository,
): ViewModel() {
    private val _searchedImages = MutableStateFlow<PagingData<UnsplashResponse>>(PagingData.empty())
    val searchedImages = _searchedImages

    val query = mutableStateOf("")
    val loading = mutableStateOf(false)

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setQuery(query: String) {
        this.query.value = query
    }

    fun searchHeroes(query: String) {
        viewModelScope.launch {
            repository.imageList(query = query).cachedIn(viewModelScope).collect {
                _searchedImages.value = it
            }
        }
    }
}