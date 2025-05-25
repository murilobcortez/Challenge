package com.challenge.shopping.fruits.data.model

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FruitResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    var image: ImageBitmap? = null,
    @SerialName("family") val family: String? = null,
    @SerialName("order") val order: String? = null,
    @SerialName("genus") val genus: String? = null
)