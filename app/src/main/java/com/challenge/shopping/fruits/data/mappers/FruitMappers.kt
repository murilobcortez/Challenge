package com.challenge.shopping.fruits.data.mappers

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.challenge.shopping.fruits.data.model.FruitEntity
import com.challenge.shopping.fruits.data.model.FruitResponse
import com.challenge.shopping.fruits.domain.model.Fruit
import java.text.NumberFormat
import java.util.Locale
import kotlin.random.Random

internal fun FruitResponse.toFruit(): Fruit {
    return Fruit(
        id = id,
        name = name,
        image = null,
        family = family.orEmpty(),
        order = order.orEmpty(),
        genus = genus.orEmpty(),
        price = generateRandomPrice(),
    )
}

internal fun FruitEntity.toFruit(): Fruit {
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

internal fun Fruit.toFruitEntity(): FruitEntity {
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

private fun generateRandomPrice(
    minimumPrice: Int = 1,
    maximumPrice: Int = 30,
    locale: Locale = Locale.US
): String {
    val priceGenerated = Random.nextInt(from = minimumPrice, until = maximumPrice)
    val priceCurrency = NumberFormat.getCurrencyInstance(locale)
    return priceCurrency.format(priceGenerated)
}
