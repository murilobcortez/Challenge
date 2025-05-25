package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.EmptyResult
import com.challenge.shopping.fruits.domain.stub.TestStub
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AddFruitOnCartUseCaseTest {

    private lateinit var repository: FruitsRepository
    private lateinit var useCase: AddFruitOnCartUseCase

    @BeforeEach
    fun setUp() {
        repository = mock()
        useCase = AddFruitOnCartUseCase(repository)
    }

    @Test
    fun `should return success when repository adds fruit successfully`() = runTest {
        // Given
        val fruit = TestStub.generateFruit()
        val expectedResult: EmptyResult<DataError.Local> = com.challenge.shopping.fruits.common.domain.Result.Success(Unit)
        whenever(repository.addFruitOnCart(fruit)).thenReturn(expectedResult)

        // When
        val result = useCase.invoke(fruit)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should return disk full error when repository fails to add fruit`() = runTest {
        // Given
        val fruit = TestStub.generateFruit()
        val error = DataError.Local.DISK_FULL
        val expectedResult: EmptyResult<DataError.Local> = com.challenge.shopping.fruits.common.domain.Result.Error(error)
        whenever(repository.addFruitOnCart(fruit)).thenReturn(expectedResult)

        // When
        val result = useCase.invoke(fruit)

        // Then
        assertEquals(expectedResult, result)
    }
}