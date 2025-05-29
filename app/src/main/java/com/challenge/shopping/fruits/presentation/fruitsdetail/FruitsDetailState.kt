package com.challenge.shopping.fruits.presentation.fruitsdetail

import com.challenge.shopping.fruits.domain.model.Fruit

internal data class FruitsDetailState(
    val isLoading: Boolean = false,
    val isAddedToCart: Boolean = false,
    val fruit: Fruit? = null
)