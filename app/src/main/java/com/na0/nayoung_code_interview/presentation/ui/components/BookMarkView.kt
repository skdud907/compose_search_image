package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun BookMarkView(
    padding: PaddingValues,
    loading: Boolean,
    likeImage: List<LikeImageEntity>,
    onNavigateToImageDetailScreen: (LikeImageEntity) -> Unit,
    onBookMarkClick: (LikeImageEntity) -> Unit,
    likeIds: List<String>,
) {
    Box(modifier = Modifier
        .padding(padding)
    ) {
        if (loading && likeImage.isEmpty()) {
            CircularIndeterminateProgressBar(isDisplayed = loading)
        } else if (likeImage.isEmpty()) {
            NothingHere()
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                contentPadding = padding,
                content = {
                    itemsIndexed(
                        items = likeImage
                    ) { index, likeImage ->
                        var id = ""
                        for (likeId in likeIds) {
                            if (likeId == likeImage.id) {
                                id = likeId
                            }
                        }

                        BookMarkCard(
                            likeImages = likeImage,
                            onClick = { onNavigateToImageDetailScreen(likeImage) },
                            onBookMarkClick = { onBookMarkClick(likeImage) },
                            likeId = id
                        )
                    }
                }
            )
        }
    }
}