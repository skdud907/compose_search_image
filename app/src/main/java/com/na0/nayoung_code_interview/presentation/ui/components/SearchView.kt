package com.na0.nayoung_code_interview.presentation.ui.components

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.na0.nayoung_code_interview.model.UnsplashResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun SearchView(
    padding: PaddingValues,
    loading: Boolean,
    recipes: List<UnsplashResponse>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToRecipeDetailScreen: (UnsplashResponse) -> Unit,
) {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface).padding(padding)
    ) {
        if (loading && recipes.isEmpty()) {
            HorizontalDottedProgressBar()
//            LoadingRecipeListShimmer(imageHeight = 250.dp,)
        } else if (recipes.isEmpty()) {
            NothingHere()
        } else {
            LazyColumn{
                itemsIndexed(
                    items = recipes
                ) { index, search ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }
                    SearchCard(
                        search = search,
                        onClick = { onNavigateToRecipeDetailScreen(search) }
                    )
                }
            }
        }
    }
}
