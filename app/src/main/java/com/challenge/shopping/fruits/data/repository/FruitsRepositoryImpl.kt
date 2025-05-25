package com.challenge.shopping.fruits.data.repository

import androidx.compose.ui.graphics.ImageBitmap
import androidx.sqlite.SQLiteException
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.EmptyResult
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.common.domain.map
import com.challenge.shopping.fruits.data.datasource.local.FruitsLocalDataSource
import com.challenge.shopping.fruits.data.datasource.remote.FruitsRemoteDataSource
import com.challenge.shopping.fruits.data.mappers.toFruit
import com.challenge.shopping.fruits.data.mappers.toFruitEntity
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PAGINATION_ITEMS_LIMIT = 20

class FruitsRepositoryImpl(
    private val localDataSource: FruitsLocalDataSource,
    private val remoteDataSource: FruitsRemoteDataSource
): FruitsRepository {

    // Remote
    override suspend fun getAllFruits(): Result<List<Fruit>, DataError> {
        return remoteDataSource
            .getAllFruits()
            .map { fruitsResponse ->
                fruitsResponse.map {
                    it.toFruit()
                }.take(PAGINATION_ITEMS_LIMIT)
            }
    }

    override suspend fun getFruitAiGeneratedImage(fruitName: String): Result<ImageBitmap, DataError> {
        return remoteDataSource.getFruitAiGeneratedImage(fruitName)
    }

    // Local
    override fun getAllFruitsOnCart(): Flow<List<Fruit>> {
        return localDataSource
            .getFruitsOnCart()
            .map { fruitEntities ->
                fruitEntities.map {
                    it.toFruit()
                }
            }
    }

    override suspend fun addFruitOnCart(fruit: Fruit): EmptyResult<DataError.Local> {
        return try {
            localDataSource.upsertFruitOnCart(
                fruit.toFruitEntity()
            )
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFruitFromCart(id: Int): EmptyResult<DataError.Local> {
        return try {
            localDataSource.deleteFruitFromCart(id)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun isFruitOnCart(id: Int): Flow<Boolean> {
        return localDataSource
            .getFruitsOnCart()
            .map { fruitEntities ->
                fruitEntities.any { it.id == id }
            }
    }
}