package com.challenge.shopping.fruits.common.utils

import androidx.compose.ui.graphics.ImageBitmap

fun emptyImageBitmap() = ImageBitmap(1, 1)

fun isEmptyImageBitmap(image: ImageBitmap): Boolean =
    image.width == 1 && image.height == 1
