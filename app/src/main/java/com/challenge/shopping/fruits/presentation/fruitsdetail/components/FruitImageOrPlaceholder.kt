package com.challenge.shopping.fruits.presentation.fruitsdetail.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.challenge.R

@Composable
fun FruitImageOrPlaceholder(image: ImageBitmap?) {
    if (image == null || isEmptyImageBitmap(image)) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_image_not_found),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    } else {
        Image(
            bitmap = image,
            contentDescription = stringResource(R.string.fruit_detail_screen_fruit_image),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
        )
    }
}

private fun isEmptyImageBitmap(image: ImageBitmap): Boolean =
    image.width == 1 && image.height == 1