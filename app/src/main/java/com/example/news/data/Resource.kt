package com.example.news.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

sealed interface Resource<out T> {
    companion object {
        fun <T> fromSource(
            sourceCall: suspend () -> T,
            onError: (Throwable) -> String = { it.message.orEmpty() }
        ) = flow {
            emit(Loading)

            val result = Success(sourceCall())
            emit(result)
        }.catch { throwable ->
            Timber.e(throwable)
            val message = onError(throwable)
            emit(Failure(message))
        }.flowOn(Dispatchers.IO)

        fun <T> fromFlowSource(
            sourceCall: suspend () -> Flow<T>,
            onError: (Throwable) -> String = { it.message.orEmpty() }
        ) = flow {
            emit(Loading)

            val result = sourceCall().map { Success(it) }
            emitAll(result)
        }.catch { throwable ->
            Timber.e(throwable)
            val message = onError(throwable)
            emit(Failure(message))
        }.flowOn(Dispatchers.IO)
    }
}

object Initial : Resource<Nothing>
data class Success<out T>(val data: T) : Resource<T>
object Loading : Resource<Nothing>
data class Failure(val message: String) : Resource<Nothing>

inline fun <T> Resource<T>.onSuccess(onSuccess: (data: T) -> Unit): Resource<T> {
    if (this is Success) onSuccess(data)
    return this
}

inline fun <T> Resource<T>.onFailure(onFailure: (message: String) -> Unit): Resource<T> {
    if (this is Failure) onFailure(message)
    return this
}

inline fun <T> Resource<T>.onLoading(onLoading: () -> Unit): Resource<T> {
    if (this is Loading) onLoading()
    return this
}