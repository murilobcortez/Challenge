package com.challenge.shopping.fruits.common.data

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import retrofit2.Response

inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, DataError.Remote> {
    return when (response.code()) {
        in 200..299 -> {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}
