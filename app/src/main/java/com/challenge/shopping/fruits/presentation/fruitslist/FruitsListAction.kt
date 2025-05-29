package com.challenge.shopping.fruits.presentation.fruitslist

import com.challenge.shopping.fruits.domain.model.Fruit

internal sealed interface FruitsListAction {
    data class OnFruitClick(val fruit: Fruit): FruitsListAction
    data class OnTapSelected(val index: Int): FruitsListAction
}