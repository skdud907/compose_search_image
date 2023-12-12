package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            val image = loadPicture(url = detail.urls.thumb, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT.dp)
                    ,
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ){
                    Text(
                        text = detail.user.name,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start)
                        ,
                        style = MaterialTheme.typography.h3
                    )
                    val rank = detail.likes.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                        ,
                        style = MaterialTheme.typography.h5
                    )
                }
                val updated = detail.description
                Text(
                    text = "Updated ${updated} by ${detail.description}"
                    ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    style = MaterialTheme.typography.caption
                )
//                for(ingredient in search.ingredients){
//                    Text(
//                        text = ingredient,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 4.dp)
//                        ,
//                        style = MaterialTheme.typography.body1
//                    )
//                }
            }
        }
    }
}
