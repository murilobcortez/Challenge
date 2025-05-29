package com.challenge.shopping.fruits.presentation.fruitslist

import com.challenge.shopping.fruits.common.presentation.UiText
import com.challenge.shopping.fruits.domain.model.Fruit

internal data class FruitsListState(
    val fruits: List<Fruit> = emptyList(),
    val fruitsAddedOnCart: List<Fruit> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
