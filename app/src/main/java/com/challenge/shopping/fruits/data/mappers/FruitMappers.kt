package com.challenge.shopping.fruits.data.mappers

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.challenge.shopping.fruits.data.model.FruitEntity
import com.challenge.shopping.fruits.data.model.FruitResponse
import com.challenge.shopping.fruits.domain.model.Fruit
import java.text.NumberFormat
import java.util.Locale
import kotlin.random.Random


fun FruitResponse.toFruit(): Fruit {
    return Fruit(
        id = id,
        name = name,
        image = image,
        family = family.orEmpty(),
        order = order.orEmpty(),
        genus = genus.orEmpty(),
        price = generateRandomPrice(),
    )
}

fun FruitEntity.toFruit(): Fruit {
    return Fruit(
        id = id,
        name = name,
        image = image?.asImageBitmap(),
        family = family,
        order = order,
        genus = genus,
        price = price
    )
}

fun Fruit.toFruitEntity(): FruitEntity {
    return FruitEntity(
        id = id,
        name = name,
        image = image?.asAndroidBitmap(),
        family = family,
        order = order,
        genus = genus,
        price = price
    )
}

private fun generateRandomPrice(): String {
        val priceUpperLimit = 30
        val priceGenerated = Random.nextInt(priceUpperLimit)
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US)

        return formattedPrice.format(priceGenerated)
}
