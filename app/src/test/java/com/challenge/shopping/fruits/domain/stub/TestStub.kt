package com.challenge.shopping.fruits.domain.stub

import com.challenge.shopping.fruits.domain.model.Fruit

object TestStub {
    fun generateFruitsList(): List<Fruit> {
        return listOf(
            Fruit(
                id = 6,
                name = "Apple",
                image = null,
                family = "Rosaceae",
                order = "Rosales",
                genus = "Malus",
                price = "R$ 10.0"
            ),
            Fruit(
                id = 1,
                name = "Banana",
                image = null,
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
            image = null,
            family = "Rosaceae",
            order = "Rosales",
            genus = "Malus",
            price = "R$ 10.0"
        )
    }
}