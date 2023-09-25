package com.mmfsin.noexcuses.base

abstract class BaseUseCaseNoParams<T> {
    abstract suspend fun execute(): T
}