package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.model.db.LikeImageEntity
import com.na0.nayoung_code_interview.util.DEFAULT_IMAGE_IMAGE
import com.na0.nayoung_code_interview.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun LikeDetailView(
    likedDetail: LikeImageEntity,
    onDetailBookMarkClick: () -> Unit,
    likeId: String
) {
    val isLiked = remember { mutableStateOf(false) }
    LaunchedEffect(likeId) {
        isLiked.value = (likeId == likedDetail.id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyColumn {
            item {
                val image =
                    loadPicture(url = likedDetail.urls, defaultImage = DEFAULT_IMAGE_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "${likedDetail.description}",
                        modifier = Modifier
                            .size(500.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
                TableCellItem(likedDetail)
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        onClick = {
                            onDetailBookMarkClick()

                            isLiked.value = !isLiked.value
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .size(48.dp)
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
}

@Composable
fun TableCellItem(likedDetail: LikeImageEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("ID", Modifier.weight(1f))
            TableContentCell("${likedDetail.id}", Modifier.weight(2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("Author", Modifier.weight(1f))
            TableContentCell("${likedDetail.user}", Modifier.weight(2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("Size", Modifier.weight(1f))
            TableContentCell("${likedDetail.width} x ${likedDetail.height}", Modifier.weight(2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("Created At", Modifier.weight(1f))
            TableContentCell("${likedDetail.createdAt}", Modifier.weight(2f))
        }
    }
}
