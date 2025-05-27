package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.EmptyResult
import com.challenge.shopping.fruits.domain.repository.FruitsRepository

class DeleteFruitFromCartUseCase(
    private val repository: FruitsRepository
) {
    suspend operator fun invoke(id: Int): EmptyResult<DataError.Local>{
        return repository.deleteFruitFromCartById(id)
    }
}