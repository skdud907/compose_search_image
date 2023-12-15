package com.na0.nayoung_code_interview.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import com.na0.nayoung_code_interview.repository.LikeImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val repository: LikeImageRepository,
): ViewModel() {
    var likedImageList = mutableStateListOf<String>()
    var isLike = mutableStateOf(false)

    private val _allImages = mutableStateOf<List<LikeImageEntity>>(emptyList())
    val allImages: State<List<LikeImageEntity>> get() = _allImages

    val loading = mutableStateOf(false)

    fun onSearchBookMarkClick(data: UnsplashResponse) {
        if (likedImageList.contains(data.id)) {
            isLike.value = false
            likedImageList.remove(data.id)
            removeSearchLikedImage(data)
        } else {
            isLike.value = true
            likedImageList.add(data.id)
            addSearchLikedImage(data)
        }
    }

    fun onBookMarkClick(data: LikeImageEntity) {
        if (likedImageList.contains(data.id)) {
            isLike.value = false
            likedImageList.remove(data.id)
            removeLikedImage(data)
        } else {
            isLike.value = true
            likedImageList.add(data.id)
            addLikedImage(data)
        }
    }

    fun onDetailBookMarkClick(data: UnsplashResponse) {
        if (likedImageList.contains(data.id)) {
            isLike.value = false
            likedImageList.remove(data.id)
            removeSearchLikedImage(data)
        } else {
            isLike.value = true
            likedImageList.add(data.id)
            addSearchLikedImage(data)
        }
    }

    fun onLikeDetailBookMarkClick(data: LikeImageEntity) {
        if (likedImageList.contains(data.id)) {
            isLike.value = false
            likedImageList.remove(data.id)
            removeLikedImage(data)
        } else {
            isLike.value = true
            likedImageList.add(data.id)
            addLikedImage(data)
        }
    }

    fun getAll() {
        viewModelScope.launch {
            _allImages.value = repository.getAllLikedImage()
        }
    }

    private fun addSearchLikedImage(data: UnsplashResponse) {
        viewModelScope.launch {
            repository.insert(
                LikeImageEntity(
                    id = data.id,
                    description = data.description ?: "",
                    width = data.width,
                    height = data.height,
                    createdAt = data.created_at,
                    likes = isLike.value,
                    urls = data.urls.regular,
                    user = data.user.name,
                )
            )
        }
    }

    private fun removeSearchLikedImage(data: UnsplashResponse) {
        viewModelScope.launch {
            repository.delete(
                LikeImageEntity(
                    id = data.id,
                    description = data.description ?: "",
                    width = data.width,
                    height = data.height,
                    createdAt = data.created_at,
                    likes = isLike.value,
                    urls = data.urls.regular,
                    user = data.user.name,
                )
            )
        }
    }

    private fun addLikedImage(data: LikeImageEntity) {
        viewModelScope.launch {
            repository.insert(
                LikeImageEntity(
                    id = data.id,
                    description = data.description ?: "",
                    width = data.width,
                    height = data.height,
                    createdAt = data.createdAt,
                    likes = isLike.value,
                    urls = data.urls,
                    user = data.user,
                )
            )
        }
    }

    private fun removeLikedImage(data: LikeImageEntity) {
        viewModelScope.launch {
            repository.delete(
                LikeImageEntity(
                    id = data.id,
                    description = data.description ?: "",
                    width = data.width,
                    height = data.height,
                    createdAt = data.createdAt,
                    likes = isLike.value,
                    urls = data.urls,
                    user = data.user,
                )
            )
        }
    }

    private fun updateLikedImage(data: LikeImageEntity) {
        viewModelScope.launch {
            repository.update(
                LikeImageEntity(
                    id = data.id,
                    description = data.description ?: "",
                    width = data.width,
                    height = data.height,
                    createdAt = data.createdAt,
                    likes = isLike.value,
                    urls = data.urls,
                    user = data.user,
                )
            )
        }
    }
}