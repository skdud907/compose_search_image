package com.na0.nayoung_code_interview.presentation.detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.presentation.detail.DetailState.GetDetailState
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_RECIPE = "recipe.state.recipe.key"

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val state: SavedStateHandle,
): ViewModel() {
    val detail: MutableState<UnsplashResponse?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        state.get<UnsplashResponse>(STATE_KEY_RECIPE)?.let{ unsplashResponse ->
            onTriggerEvent(GetDetailState(unsplashResponse))
        }
    }

    fun onTriggerEvent(event: DetailState) {
        viewModelScope.launch {
            try {
                when(event) {
                    is GetDetailState -> {
                        if (detail.value == null) {
                            getDetail(event.unsplashResponse)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(Constants.TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getDetail(unsplashResponse: UnsplashResponse) {
        loading.value = true

        delay(1000)

        this.detail.value = unsplashResponse

        state.set(STATE_KEY_RECIPE, detail.value!!.id)

        loading.value = false
    }
}