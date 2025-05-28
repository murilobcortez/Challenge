package com.challenge.shopping.fruits.presentation.fruitslist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.challenge.R
import com.challenge.shopping.fruits.common.utils.isEmptyImageBitmap

@Composable
fun FruitImageOrPlaceholder(image: ImageBitmap?, contentDescription: String) {
    when {
        image == null -> {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
        isEmptyImageBitmap(image) -> {
            Image(
                painter = painterResource(R.drawable.ic_image_not_found),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        else -> {
            Image(
                bitmap = image,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}