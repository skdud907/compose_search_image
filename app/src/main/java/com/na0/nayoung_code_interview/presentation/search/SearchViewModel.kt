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
import javax.inject.Named
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.util.Constants.TAG

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val repository: ImageRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    val images: MutableState<List<UnsplashResponse>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

//    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    // Pagination starts at '1' (-1 = exhausted)
    val page = mutableStateOf(1)

    val perPage = mutableStateOf(30)

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
//        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
//            setSelectedCategory(c)
//        }

        if(recipeListScrollPosition != 0){
            onTriggerEvent(SearchState.RestoreStateState)
        }
        else{
            onTriggerEvent(SearchState.NewSearchState)
        }

    }

    fun onTriggerEvent(event: SearchState){
        viewModelScope.launch {
            try {
                when(event){
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
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value = true

        val data = repository.search(query.value, page.value, perPage.value)

        Log.d("Response Success", "size = ${data.results.size}, results = ${data.results}")
        val photo = data.results

        for (result in photo) {
            searchResponse.add(result)
        }

        images.value = searchResponse
        loading.value = false

//        val results: MutableList<ImagesResponse> = mutableListOf()
//        for(p in 1..page.value){
//            val result = repository.search(
//                perPage = perPage.value,
//                page = p,
//                query = query.value
//            )
//            results.addAll(result)
//            if(p == page.value){ // done
//                recipes.value = results
//                loading.value = false
//            }
//        }
    }

    private suspend fun newSearch() {
        loading.value = true

        resetSearchState()

        delay(2000)

        restoreState()
    }

    private suspend fun nextPage(){
        // prevent duplicate event due to recompose happening to quickly
        if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE) ){
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            // just to show pagination, api is fast
            delay(1000)

            if(page.value > 1){
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

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<UnsplashResponse>){
        val current = ArrayList(this.images.value)
        current.addAll(recipes)
        this.images.value = current
    }

    private fun incrementPage(){
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        images.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
//        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
//        setSelectedCategory(null)
//        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String) {
//        val newCategory = getFoodCategory(category)
//        setSelectedCategory(newCategory)
//        onQueryChanged(category)
    }

    private fun setListScrollPosition(position: Int){
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

//    private fun setSelectedCategory(category: FoodCategory?){
//        selectedCategory.value = category
//        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
//    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}