package com.na0.nayoung_code_interview.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import com.na0.nayoung_code_interview.util.DEFAULT_IMAGE_IMAGE
import com.na0.nayoung_code_interview.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun BookMarkCard(
    likeImages: LikeImageEntity,
    onClick: () -> Unit,
    onBookMarkClick: () -> Unit,
    likeId: String
) {
    val isLiked = remember { mutableStateOf(false) }
    LaunchedEffect(likeId) {
        isLiked.value = (likeId == likeImages.id)
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 3.dp,
                top = 3.dp,
                start = 3.dp,
                end = 3.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                val image =
                    loadPicture(url = likeImages.urls, defaultImage = DEFAULT_IMAGE_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "${likeImages.description}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop,
                    )
                }

                IconButton(
                    onClick = {
                        onBookMarkClick()

                        isLiked.value = !isLiked.value
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = if (isLiked.value) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}