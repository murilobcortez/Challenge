package com.challenge.shopping.fruits.presentation.fruitsdetail

import com.challenge.shopping.fruits.domain.model.Fruit

sealed interface FruitsDetailAction {
    data object OnBackClick: FruitsDetailAction
    data object OnAddToCartClick: FruitsDetailAction
    data class OnCartFruitsChange(val fruit: Fruit): FruitsDetailAction
}