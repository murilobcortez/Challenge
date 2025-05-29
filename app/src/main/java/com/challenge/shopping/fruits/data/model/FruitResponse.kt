package com.challenge.shopping.fruits.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FruitResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("family") val family: String? = null,
    @SerialName("order") val order: String? = null,
    @SerialName("genus") val genus: String? = null
)