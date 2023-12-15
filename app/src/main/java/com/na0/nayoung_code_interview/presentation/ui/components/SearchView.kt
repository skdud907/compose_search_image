package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.na0.nayoung_code_interview.model.UnsplashResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun SearchView(
    padding: PaddingValues,
    loading: Boolean,
    images: List<UnsplashResponse>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToImageDetailScreen: (UnsplashResponse) -> Unit,
    onSearchBookMarkClick: (UnsplashResponse) -> Unit,
    likeIds: List<String>,
) {
    Box(modifier = Modifier
        .padding(padding)
    ) {
        if (loading && images.isEmpty()) {
            CircularIndeterminateProgressBar(isDisplayed = loading)
        } else if (images.isEmpty()) {
            NothingHere()
        } else {
            val listState = rememberLazyStaggeredGridState()
            LazyVerticalStaggeredGrid(
                state = listState,
                columns = StaggeredGridCells.Fixed(3),
                contentPadding = padding,
                content = {
                    itemsIndexed(
                        items = images,
                    ) { index, search ->

                        onChangeScrollPosition(index)
                        if (index == images.size - 1 && !loading) {
                            onTriggerNextPage()
                        }

                        LaunchedEffect(listState) {
                            snapshotFlow {
                                listState.layoutInfo.visibleItemsInfo
                            }.collect { infos ->
                                if (infos.any {
                                        it.index == images.size - 1
                                    }) {
                                    onTriggerNextPage()
                                }
                            }
                        }

//                        Log.d(TAG, "nextPage: index= $index, page= $page")
//                        if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
//                            onTriggerNextPage()
//                        }

                        var id = ""
                        for (likeId in likeIds) {
                            if (likeId == search.id) {
                                id = likeId
                            }
                        }
                        SearchCard(
                            search = search,
                            onClick = { onNavigateToImageDetailScreen(search) },
                            onSearchBookMarkClick = { onSearchBookMarkClick(search) },
                            likeId = id
                        )
                    }
                }
            )
        }
    }
}