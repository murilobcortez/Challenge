package com.challenge.shopping.fruits.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class Fruit(
    val id: Int,
    val image: ImageBitmap?,
    val name: String,
    val family: String,
    val order: String,
    val genus: String,
    val price: String
)