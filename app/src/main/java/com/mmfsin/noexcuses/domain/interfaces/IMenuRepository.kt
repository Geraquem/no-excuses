package com.mmfsin.noexcuses.domain.interfaces

interface IMenuRepository {
    suspend fun checkVersion()
}