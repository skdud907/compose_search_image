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
import com.na0.nayoung_code_interview.util.Constants.TAG

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    val images: MutableState<List<UnsplashResponse>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val perPage = mutableStateOf(4)
    var recipeListScrollPosition = 0
    val searchResponse = mutableListOf<UnsplashResponse>()

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

        if (recipeListScrollPosition != 0) {
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
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            delay(1000)

            if (page.value > 1) {
                val results = repository.search(query.value, page.value, perPage.value).results
                Log.d(TAG, "search: appending")

                for (result in results) {
                    searchResponse.add(result)
                }
                appendRecipes(searchResponse)
            }
            loading.value = false
        }
    }

    private fun appendRecipes(recipes: List<UnsplashResponse>) {
        val current = ArrayList(this.images.value)
        current.addAll(recipes)
        this.images.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun resetSearchState() {
        images.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
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
}