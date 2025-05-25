package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.EmptyResult
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteFruitFromCartUseCaseTest {

    private lateinit var repository: FruitsRepository
    private lateinit var useCase: DeleteFruitFromCartUseCase

    @BeforeEach
    fun setUp() {
        repository = mock()
        useCase = DeleteFruitFromCartUseCase(repository)
    }

    @Test
    fun `should call repository to delete fruit from cart`() = runTest {
        // Given
        val fruitId = 1

        // When
        useCase.invoke(fruitId)

        // Then
        verify(repository).deleteFruitFromCart(fruitId)
    }

    @Test
    fun `should return unknown error when repository fails to delete fruit`() = runTest {
        // Given
        val fruitId = 1
        val error = DataError.Local.UNKNOWN
        val expectedResult: EmptyResult<DataError.Local> = com.challenge.shopping.fruits.common.domain.Result.Error(error)
        whenever(repository.deleteFruitFromCart(fruitId)).thenReturn(expectedResult)

        // When
        val result = useCase.invoke(fruitId)

        // Then
        assertEquals(expectedResult, result)
    }
}
