package com.na0.nayoung_code_interview.presentation.search

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.na0.nayoung_code_interview.model.UnsplashResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.repository.LikeImageRepository
import com.na0.nayoung_code_interview.util.Constants.TAG

const val STATE_KEY_PAGE = "image.state.page.key"
const val STATE_KEY_QUERY = "image.state.query.key"
const val STATE_KEY_LIST_POSITION = "image.state.query.list_position"
const val STATE_LIKE = "image.state.like"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    val images: MutableState<List<UnsplashResponse>> = mutableStateOf(ArrayList())
    var searchResponse = mutableListOf<UnsplashResponse>()

    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val perPage = mutableStateOf(4)
    val isLike = mutableStateOf(false)

    var imageListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        savedStateHandle.get<Boolean>(STATE_LIKE)?.let { l ->
            setImageLike(l)
        }

        if (imageListScrollPosition != 0) {
            onTriggerEvent(SearchState.RestoreStateState)
        } else {
            onTriggerEvent(SearchState.NewSearchState)
        }
    }

    fun onTriggerEvent(event: SearchState) {
        viewModelScope.launch {
            try {
                when(event) {
                    is SearchState.NewSearchState -> {
                        newSearch()
                    }
                    is SearchState.NextPageState -> {
                        nextPage()
                    }
                    is SearchState.RestoreStateState -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun restoreState() {
        loading.value = true

        val data = repository.search(query.value, page.value, perPage.value)

        Log.d("Response Success", "size = ${data.results.size}, results = ${data.results}")
        val photo = data.results

        for (result in photo) {
            searchResponse.add(result)
        }

        images.value = searchResponse
        loading.value = false
    }

    private suspend fun newSearch() {
        loading.value = true

        resetSearchState()

        delay(2000)

        restoreState()
    }

    private suspend fun nextPage() {
        if ((imageListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")
            Log.d(TAG, "nextPage: imageListScrollPosition: ${imageListScrollPosition}")

            delay(1000)

            if (page.value > 1) {
                Log.d(TAG, "nextPage: page = ${page.value}")
                val results = repository.search(query.value, page.value, perPage.value).results
                Log.d(TAG, "search: appending")

                for (result in results) {
                    searchResponse.add(result)
                }
                appendImages(searchResponse)
            }
            loading.value = false
        }
    }

    private fun appendImages(images: List<UnsplashResponse>) {
//        val current = ArrayList(this.searchResponse)
//        current.addAll(images)
//        this.searchResponse = current
        val current = ArrayList(this.images.value)
        current.addAll(images)
        this.images.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeImageScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun resetSearchState() {
        images.value = listOf()
        page.value = 1
        searchResponse.clear()
        onChangeImageScrollPosition(0)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setListScrollPosition(position: Int) {
        imageListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    private fun setImageLike(isLike: Boolean) {
        this.isLike.value = isLike
        savedStateHandle.set(STATE_LIKE, isLike)
    }
}