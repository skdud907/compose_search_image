package com.na0.nayoung_code_interview.presentation.detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import com.na0.nayoung_code_interview.presentation.detail.DetailState.GetDetailState
import com.na0.nayoung_code_interview.repository.ImageRepository
import com.na0.nayoung_code_interview.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_IMAGE = "image.state.image.key"
const val STATE_KEY_LIKE_IMAGE = "image.state.image.like.key"

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val state: SavedStateHandle,
): ViewModel() {
    val searchDetail: MutableState<UnsplashResponse?> = mutableStateOf(null)
    val likedDetail: MutableState<LikeImageEntity?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val screenType = mutableStateOf(999)

    init {
        state.get<UnsplashResponse>(STATE_KEY_IMAGE)?.let{ unsplashResponse ->
            onTriggerEvent(GetDetailState(unsplashResponse))
        }
        state.get<LikeImageEntity>(STATE_KEY_LIKE_IMAGE)?.let{ likeImageEntity ->
            onTriggerEvent(DetailState.GetLikedDetailState(likeImageEntity))
        }
    }

    fun onTriggerEvent(event: DetailState) {
        viewModelScope.launch {
            try {
                when(event) {
                    is GetDetailState -> {
                        if (searchDetail.value == null) {
                            getDetail(event.unsplashResponse)
                        }
                    }
                    is DetailState.GetLikedDetailState -> {
                        if (searchDetail.value == null) {
                            getLikedDetail(event.likesImages)
                        }
                    }
                    is DetailState.GetScreenType -> {
                        screenType.value = event.screenType
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

        this.searchDetail.value = unsplashResponse

        state.set(STATE_KEY_IMAGE, searchDetail.value!!.id)

        loading.value = false
    }

    private suspend fun getLikedDetail(likeImageEntity: LikeImageEntity) {
        loading.value = true

        delay(1000)

        this.likedDetail.value = likeImageEntity

        state.set(STATE_KEY_LIKE_IMAGE, likedDetail.value!!.id)

        loading.value = false
    }
}