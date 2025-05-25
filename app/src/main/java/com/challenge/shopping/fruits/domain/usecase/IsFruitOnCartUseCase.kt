package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.flow.Flow

class IsFruitOnCartUseCase(
    private val repository: FruitsRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> {
        return repository.isFruitOnCart(id)
    }
}