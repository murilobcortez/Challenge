package com.challenge.shopping.fruits.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FruitGenerateImageRequest(
    val model: String,
    val prompt: String
)