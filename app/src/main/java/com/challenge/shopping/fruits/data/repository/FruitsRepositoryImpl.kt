package com.challenge.shopping.fruits.data.repository

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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

    override suspend fun generateFruitImageByName(fruitName: String): Result<ImageBitmap, DataError> {
        val result = remoteDataSource.generateFruitImageByName(fruitName)
        return when (result) {
            is Result.Success -> {
                val imageBitmap = bytesToImageBitmapOrEmpty(result.data)
                Result.Success(imageBitmap)
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    private fun bytesToImageBitmapOrEmpty(bytes: ByteArray?): ImageBitmap {
        if (bytes == null) return emptyImageBitmap()

        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        return bitmap?.asImageBitmap() ?: emptyImageBitmap()
    }

    private fun emptyImageBitmap() = ImageBitmap(1, 1)

    // Local
    override fun getAllFruitsOnCart(): Flow<List<Fruit>> {
        return localDataSource
            .getAllFruits()
            .map { fruitEntities ->
                fruitEntities.map {
                    it.toFruit()
                }
            }
    }

    override suspend fun addFruitOnCart(fruit: Fruit): EmptyResult<DataError.Local> {
        return try {
            localDataSource.upsertFruit(
                fruit.toFruitEntity()
            )
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFruitFromCartById(id: Int): EmptyResult<DataError.Local> {
        return try {
            localDataSource.deleteFruitById(id)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun isFruitOnCart(id: Int): Flow<Boolean> {
        return localDataSource
            .getAllFruits()
            .map { fruitEntities ->
                fruitEntities.any { it.id == id }
            }
    }
}