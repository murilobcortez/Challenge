package com.challenge.shopping.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object FruitsGraph: Route

    @Serializable
    data object FruitsList: Route

    @Serializable
    data class FruitsDetail(val id: Int): Route
}