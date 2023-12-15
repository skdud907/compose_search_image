package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.na0.nayoung_code_interview.model.UnsplashResponse

@Composable
fun SearchView(
    padding: PaddingValues,
    loading: Boolean,
    images: LazyPagingItems<UnsplashResponse>,
    onNavigateToImageDetailScreen: (UnsplashResponse) -> Unit,
    onSearchBookMarkClick: (UnsplashResponse) -> Unit,
    likeIds: List<String>,
) {
    Box(
        modifier = Modifier
            .padding(padding)
    ) {
        if (loading && images.itemCount == 0) {
            CircularIndeterminateProgressBar(isDisplayed = loading)
        } else if (images.itemCount == 0) {
            NothingHere()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    items(images.itemCount) { index ->
                        images[index]?.let { image ->
                            var id = ""
                            for (likeId in likeIds) {
                                if (likeId == image.id) {
                                    id = likeId
                                }
                            }
                            SearchCard(
                                search = image,
                                onClick = { onNavigateToImageDetailScreen(image) },
                                onSearchBookMarkClick = { onSearchBookMarkClick(image) },
                                likeId = id,
                            )
                        }
                    }
                }
            )
        }
    }
}