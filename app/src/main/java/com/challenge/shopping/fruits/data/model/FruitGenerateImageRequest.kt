package com.challenge.shopping.fruits.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class FruitGenerateImageRequest(
    val model: String,
    val prompt: String
)