package com.challenge.shopping.fruits.domain.usecase

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import kotlinx.coroutines.runBlocking

class GetFruitAiGeneratedImageUseCaseTest {

    private lateinit var repository: FruitsRepository
    private lateinit var useCase: GetFruitImageUseCase

    @BeforeEach
    fun setUp() {
        repository = mock()
        useCase = GetFruitImageUseCase(repository)
    }

    @Test
    fun `should return ImageBitmap when repository returns image`() = runTest {
        // Given
        val fruitName = "Apple"
        val expectedImage: ImageBitmap = mock()
        whenever(repository.getFruitImageByName(fruitName))
            .thenReturn(Result.Success(expectedImage))

        // When
        val result = useCase.invoke(fruitName)

        // Then
        assertEquals(Result.Success(expectedImage), result)
    }


    @Test
    fun `should return error when repository returns request timeout`() = runBlocking {
        // Given
        val error = DataError.Remote.REQUEST_TIMEOUT
        val fruitName = "Banana"
        whenever(repository.getFruitImageByName(fruitName)).thenReturn(Result.Error(error))

        // When
        val result = useCase.invoke(fruitName)

        // Then
        assertEquals(Result.Error(error), result)
    }
}