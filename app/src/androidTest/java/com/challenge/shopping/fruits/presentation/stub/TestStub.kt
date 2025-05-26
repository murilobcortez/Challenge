package com.challenge.shopping.fruits.presentation.stub

import com.challenge.shopping.fruits.domain.model.Fruit

object TestStub {
    fun generateFruit(): Fruit {
        return Fruit(
            id = 1,
            name = "Banana",
            image = null,
            family = "Musaceae",
            order = "Zingiberales",
            genus = "Musa",
            price = "$1.99"
        )
    }
}