package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.na0.nayoung_code_interview.model.UnsplashResponse
import com.na0.nayoung_code_interview.util.DEFAULT_RECIPE_IMAGE
import com.na0.nayoung_code_interview.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun DetailView(
    detail: UnsplashResponse,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        item {
            val image = loadPicture(url = detail.urls.regular, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "${detail.description}",
                    modifier = Modifier
                        .size(500.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            TableCellItem(detail)
        }
    }
}

@Composable
fun TableCellItem(detail: UnsplashResponse) {
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
            TableContentCell("${detail.id}", Modifier.weight(2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("Author", Modifier.weight(1f))
            TableContentCell("${detail.user.name}", Modifier.weight(2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("Size", Modifier.weight(1f))
            TableContentCell("${detail.width} x ${detail.height}", Modifier.weight(2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TableTitleCell("Created At", Modifier.weight(1f))
            TableContentCell("${detail.created_at}", Modifier.weight(2f))
        }
    }
}


@Composable
fun TableTitleCell(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun TableContentCell(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.body2)
        }
    }
}