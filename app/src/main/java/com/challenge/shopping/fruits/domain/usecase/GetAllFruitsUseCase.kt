package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.domain.repository.FruitsRepository

class GetAllFruitsUseCase(
    private val repository: FruitsRepository
) {
    suspend operator fun invoke(): Result<List<Fruit>, DataError>{
        return repository.getAllFruits()
    }
}