package com.challenge.shopping.fruits.presentation.stub

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.domain.model.Fruit
import org.mockito.Mockito

object TestStub {
    fun generateFruitsList(image: ImageBitmap? = null): List<Fruit> {
        return listOf(
            Fruit(
                id = 6,
                name = "Apple",
                image = image,
                family = "Rosaceae",
                order = "Rosales",
                genus = "Malus",
                price = "R$ 10.0"
            ),
            Fruit(
                id = 1,
                name = "Banana",
                image = image,
                family = "Musaceae",
                order = "Zingiberales",
                genus = "Musa",
                price = "R$ 15.0"
            )
        )
    }

    fun generateFruit(): Fruit {
        return Fruit(
            id = 6,
            name = "Apple",
            image = Mockito.mock(ImageBitmap::class.java),
            family = "Rosaceae",
            order = "Rosales",
            genus = "Malus",
            price = "R$ 10.0"
        )
    }
}