package com.na0.nayoung_code_interview.presentation.bookmark

import android.net.http.SslCertificate.restoreState
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.presentation.detail.DetailState
import com.na0.nayoung_code_interview.presentation.detail.STATE_KEY_RECIPE
import com.na0.nayoung_code_interview.presentation.search.SearchState
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_LIKE_IMAGE = "image.state.image.like"

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val state: SavedStateHandle,
): ViewModel() {
    val likesImages: MutableState<List<UnsplashResponse>> = mutableStateOf(ArrayList())
    val loading = mutableStateOf(false)
    var bookmarkListScrollPosition = 0

    init {
        state.get<List<UnsplashResponse>>(STATE_LIKE_IMAGE)?.let{ unsplashResponses ->
            onTriggerEvent(BookMarkState.GetBookMarkState(unsplashResponses))
        }
    }

    fun onTriggerEvent(event: BookMarkState) {
        viewModelScope.launch {
            try {
                when(event) {
                    is BookMarkState.GetBookMarkState -> {
                        getLikeImages(event.unsplashResponses)
                    }
                }
            } catch (e: Exception) {
                Log.e(Constants.TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(Constants.TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun getLikeImages(unsplashResponses: List<UnsplashResponse>) {
        loading.value = true

        delay(1000)

        this.likesImages.value = unsplashResponses

        state.set(STATE_LIKE_IMAGE, likesImages.value)

        loading.value = false
    }
}