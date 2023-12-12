package com.na0.nayoung_code_interview.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

//@Composable
//fun ImageItem(
//    items: UnsplashResponse,
//    onNavigateToRecipeDetailScreen: (Int) -> Unit,
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable {
////                onStaggeredGridItemClick(navController, items.id)
//            },
//    ) {
//        val painter = rememberImagePainter(items.urls.thumb)
//
//        Image(
//            painter = painter,
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .clip(MaterialTheme.shapes.medium),
//            contentScale = ContentScale.Crop
//        )
//
//        IconButton(
//            onClick = { /* Handle button click */ },
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.splash),
//                    contentDescription = null,
//                    modifier = Modifier.size(24.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
//    }
//}