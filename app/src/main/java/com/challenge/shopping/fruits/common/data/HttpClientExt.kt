package com.challenge.shopping.fruits.common.data

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    crossinline execute: suspend () -> Response<T>
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: IOException) {
        return Result.Error(DataError.Remote.NETWORK)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, DataError.Remote> {
    return when {
        response.isSuccessful -> {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        response.code() == 408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        response.code() == 429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        response.code() in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}
