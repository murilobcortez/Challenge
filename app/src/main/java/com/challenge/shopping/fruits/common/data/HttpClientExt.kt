package com.challenge.shopping.fruits.common.data

import android.util.Log
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import kotlinx.coroutines.ensureActive
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: IOException) {
        // IOException cobre timeout, falta de internet, etc.
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: HttpException) {
        return Result.Error(DataError.Remote.SERVER)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Log.e("Logger", e.message.toString())
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

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
