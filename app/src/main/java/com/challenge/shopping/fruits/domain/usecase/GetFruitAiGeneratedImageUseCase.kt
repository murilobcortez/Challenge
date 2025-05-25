package com.challenge.shopping.fruits.domain.usecase

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.repository.FruitsRepository

class GetFruitAiGeneratedImageUseCase(
    private val repository: FruitsRepository
) {
    suspend operator fun invoke(fruitName: String): Result<ImageBitmap, DataError> {
        return repository.getFruitAiGeneratedImage(fruitName)
    }
}