package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.EmptyResult
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.repository.FruitsRepository

class AddFruitOnCartUseCase(
    private val repository: FruitsRepository
) {
    suspend operator fun invoke(fruit: Fruit): EmptyResult<DataError.Local>{
        return repository.addFruitOnCart(fruit)
    }
}